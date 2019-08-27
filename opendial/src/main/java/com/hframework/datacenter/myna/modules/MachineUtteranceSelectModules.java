package com.hframework.datacenter.myna.modules;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hframework.common.util.RegexUtils;
import com.hframework.datacenter.myna.FAQRepository;
import com.hframework.datacenter.myna.descriptor.JudgmentBranchDescriptor;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.modules.Module;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class MachineUtteranceSelectModules extends AbstractModule implements Module {
    private static final Logger logger = LoggerFactory.getLogger(MachineUtteranceSelectModules.class);
    private int askRepeatCounter = 0;
    private int askRepeatTotalCounter = 0;

    public MachineUtteranceSelectModules(DialogueSystem system) {
        super(system);
    }

    @Override
    public void trigger(DialogueState state, Collection<String> updatedVars) {
        if(DialogSystemHelper.isBackwardReasoningStage(system)) return ;
        resetAskUtteranceWithCalc(state, updatedVars);
        selectAskRepeatUtterance(state, updatedVars);
    }


    private void selectAskRepeatUtterance(DialogueState state, Collection<String> updatedVars) {
        if (updatedVars.contains("a_m") && state.hasChanceNode("a_m")) {
            String am = state.queryProb("a_m").getBest().toString();
            if("AskRepeat".equals(am)) {
                QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(state);
                if(currentQnaDescriptor == null) {
                    return ;
                }

                if(askRepeatTotalCounter + 1 >= currentQnaDescriptor.getBreakSessionThreshold()) {
                    system.addContent("current_step", "Close");
                    askRepeatTotalCounter = 0;
                }else if(this.askRepeatCounter + 1 >= currentQnaDescriptor.getBreakQAThreshold()) {
                    DialogSystemHelper.addBreakStep(system, currentQnaDescriptor.getCode());
                    String nextSlotCode = currentQnaDescriptor.getNext();//分支结构如果没有回答，直接走false分支
                    if(currentQnaDescriptor.getBranchs() != null) {
                        for (JudgmentBranchDescriptor judgmentBranchDescriptor : currentQnaDescriptor.getBranchs()) {
                            if(judgmentBranchDescriptor.getValue().equals("false")){
                                nextSlotCode = judgmentBranchDescriptor.getNext();
                                break;
                            }
                        }
                    }

                    system.addContent("current_step", nextSlotCode);
                    this.askRepeatCounter = 0;
                }else {

                    //是否为离线后现成切入重新作答，比如：你在吗，回答在！
                    TimeOutModules timeOutModules = system.getModule(TimeOutModules.class);
                    int askRepeatCounter = timeOutModules == null ? 0 : timeOutModules.getAskRepeatCounter();
                    String originUtterance = timeOutModules == null ? "" : timeOutModules.getOriginUtterance();
                    if(askRepeatCounter > 0 && StringUtils.isNotBlank(originUtterance)) {//超时回来后不识别回答
                        DialogSystemHelper.breakP2pReplyStage(system);
                        if(currentQnaDescriptor.isEndingQA()) {
                            system.addContent("u_m", originUtterance);
                            return;//如果是道别语，如果客户长时间没有作答，回来后，不能说“继续刚才的话题”
                        }else {
                            system.addContent("u_m", "继续刚才的话题，" + originUtterance);
                            return;
                        }
                    }

                    //FAQ后置切换，前置切换默认走信息askRepeat在这里进行处理
                    if(getCurrentTaskNode(state).getObject().getFaqPattern() == (byte)1 ||
                            getCurrentTaskNode(state).getObject().getFaqPattern() == (byte)0) {
                        //调用任务知识库进行进一步作答分析
                        String answer = FAQRepository.search(getVariableBestValue(state, "u_u"));
                        if(StringUtils.isNotBlank(answer)) {
                            DialogSystemHelper.intoP2pReplyStage(system);
                            system.addContent("u_m", answer);
                            return;
                        }
                    }


                    List<String> askRepeatUtterances = currentQnaDescriptor.getAskRepeatUtterances();
                    String utterance = askRepeatUtterances.get(this.askRepeatCounter++ % askRepeatUtterances.size());
                    Set<String> optionSet = currentQnaDescriptor.getOptions().keySet();
                    boolean moreThanThree = optionSet.size() > 3;
                    DialogSystemHelper.breakP2pReplyStage(system);
                    List<String> options = Lists.newArrayList(optionSet).subList(0, moreThanThree ? 3 : optionSet.size());
                    utterance = utterance.replace("{OPTIONS}", Joiner.on("、").join(options) + (moreThanThree ? "等":""));
                    system.addContent("u_m", utterance);
                    askRepeatTotalCounter ++;
                }
            }else {
                askRepeatCounter = 0;
                askRepeatTotalCounter--;

            }

        }
    }

    private void resetAskUtteranceWithCalc(DialogueState state, Collection<String> updatedVars) {
        if (updatedVars.contains("current_step") && state.hasChanceNode("current_step")) {
            QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(state);
            if(currentQnaDescriptor == null) {
                return ;
            }
            String utterance = currentQnaDescriptor.getUtterance();
            if(StringUtils.isBlank(utterance)) return ;
            String[] optionStrs= RegexUtils.find(utterance, "&lt;[^&]+&gt;");
            if(optionStrs.length > 0 || utterance.contains("×")){
                Map<String, String> varValueMap = getUsedVarValueInfo(utterance, state);

                if(optionStrs.length > 0){
                    for (String optStr : optionStrs) {
                        String[] optionVars = RegexUtils.find(optStr, "\\{[^}]+\\}");
                        for (String var1 : optionVars) {
                            if(notExists(var1, varValueMap)) {
                                utterance = utterance.replace(optStr, "");
                                break;
                            }
                        }
                        utterance = utterance.replace(optStr, optStr.replace("&lt;", "").replace("&gt;", ""));
                    }
                }

                String[] optionVars = RegexUtils.find(utterance, "\\{[^}]+\\}");
                for (String optionVar : optionVars) {
                    Collection<? extends String> vars1 = getVars(optionVar);
                    if(vars1.size() == 1) {
                        String value = varValueMap.get(vars1.iterator().next());
                        utterance = utterance.replace(optionVar, value == null ? "" : value);
                    }else {
                        BigDecimal total = BigDecimal.ONE;
                        for (String var : vars1) {
                            total = total.multiply(new BigDecimal(varValueMap.get(var)));
                        }
                        utterance = utterance.replace(optionVar, total.toPlainString());
                    }
                }
                system.addContent("u_m",utterance);
            }
        }
    }

    private Map<String, String> getUsedVarValueInfo(String utterance, DialogueState state) {
        Map<String, String> varValueMap = new HashMap<>();
        Set<String> vars  = new HashSet<>();
        String[] tempVars = RegexUtils.find(utterance, "\\{[^}]+\\}");
        for (String var1 : tempVars) {
            vars.addAll(getVars(var1));
        }
        for (String var : vars) {
            if(state.hasNode(var)){
                String value = state.getChanceNode(var).getValues().iterator().next().toString();
                varValueMap.put(var, value);
            }
        }
        return varValueMap;
    }

    private boolean notExists(String var1, Map<String, String> varValueMap) {
        for (String var : getVars(var1)) {
            if(!varValueMap.containsKey(var)){
                return true;
            }
        }
        return false;
    }

    public Collection<? extends String> getVars(String var1) {
        HashSet<String> strings = Sets.newHashSet(var1.substring(1, var1.length() - 1).split("×"));
        strings.remove(null);
        strings.remove("");
        return strings;
    }
}
