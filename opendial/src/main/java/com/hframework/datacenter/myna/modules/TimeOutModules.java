package com.hframework.datacenter.myna.modules;

import com.google.common.collect.Lists;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.datacenter.myna.ConfigurationEnums;
import com.hframework.datacenter.myna.RobotManager;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.datacenter.myna.parser.ISemanticParser;
import com.hframework.myna.config.domain.model.SemanticParser;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.bn.distribs.CategoricalTable;
import opendial.bn.distribs.IndependentDistribution;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import opendial.modules.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeOutModules extends AbstractModule implements Module {
    private static final Logger logger = LoggerFactory.getLogger(TimeOutModules.class);

    private volatile long lastTimestamp = -1;
    private volatile int waitTimeOutSecond;
    private volatile String originUtterance = null;//TODO 需要增加浮动节点当用户回复在时，进行问题重提

    private volatile int askRepeatCounter = 0;

    private Thread timeoutThread;

    public TimeOutModules(DialogueSystem system) {
        super(system);
        final DialogueSystem finalSystem = system;
        timeoutThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000L);
                        if(lastTimestamp == -1) continue;
                        if(lastTimestamp < System.currentTimeMillis() - waitTimeOutSecond * 1000 * (askRepeatCounter + 1)) {
                            QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(finalSystem.getState());
                            if(currentQnaDescriptor == null) break;
                            DialogSystemHelper.intoTimeOutStatus(finalSystem);

                            if(DialogSystemHelper.isP2pReplyStage(finalSystem)) {
                                DialogSystemHelper.breakP2pReplyStage(finalSystem);
                                finalSystem.addContent("u_m",currentQnaDescriptor.getUtterance());
                            }else if(askRepeatCounter >= currentQnaDescriptor.getBreakQAThreshold()) {
                                finalSystem.addContent("current_step", "Close");
                                askRepeatCounter = 0;
                            }else if(currentQnaDescriptor.isEndingQA()) {//如果是道别语，这不进行超时提醒，超过阈值自动关闭对话即可
                                askRepeatCounter++;
                            }else {
                                if(originUtterance == null) {
//                                    originUtterance = finalSystem.getState().queryProb("u_m").getBest().toString();//上一句不一定是问题本身
                                    originUtterance = currentQnaDescriptor.getUtterance();
                                }
                                List<String> waitTimeOutUtterances = getWaitTimeOutUtterances(currentQnaDescriptor);
                                String utterance = waitTimeOutUtterances.get(askRepeatCounter++ % waitTimeOutUtterances.size());
                                finalSystem.addContent("u_m",utterance);
                            }
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<String> getWaitTimeOutUtterances(QnaDescriptor currentQnaDescriptor){
        List<String> waitTimeOutUtterances = currentQnaDescriptor.getWaitTimeOutUtterances();
        if(waitTimeOutUtterances.size() == 0) {
            waitTimeOutUtterances = Lists.newArrayList("请问您在吗？", "您好，您还在吗", "在？");
        }
        return waitTimeOutUtterances;
    }


    @Override
    public void start() {
        super.start();
        timeoutThread.start();
    }

    @Override
    public void trigger(DialogueState state, Collection<String> updatedVars) {
        if (updatedVars.contains("u_u") && state.hasChanceNode("u_u")) {
            QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(state);
            if(currentQnaDescriptor == null) {
                return ;
            }
//            lastTimestamp = -1;
        }
        if (updatedVars.contains("u_m") && state.hasChanceNode("u_m")) {
            QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(state);
            if(currentQnaDescriptor == null
                    || "Final".equals(currentQnaDescriptor.getCode())
                    || "Close".equals(currentQnaDescriptor.getCode())) {
                return ;
            }
            List<String> waitTimeOutUtterances = getWaitTimeOutUtterances(currentQnaDescriptor);
            if(!waitTimeOutUtterances.contains(state.queryProb("u_m").getBest().toString())) {
                lastTimestamp = System.currentTimeMillis();
                waitTimeOutSecond = currentQnaDescriptor.getWaitTimeOut();
                askRepeatCounter = 0;
                originUtterance = null;
                DialogSystemHelper.breakTimeOuStatus(system);
            }
        }
    }

    public String getOriginUtterance() {
        return originUtterance;
    }

    public int getAskRepeatCounter() {
        return askRepeatCounter;
    }
}
