package com.hframework.datacenter.myna.modules;

import com.hframework.common.frame.ServiceFactory;
import com.hframework.datacenter.myna.ConfigurationEnums;
import com.hframework.datacenter.myna.FAQRepository;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.datacenter.myna.parser.CorpusMatchSemanticParser;
import com.hframework.datacenter.myna.parser.ISemanticParser;
import com.hframework.datacenter.myna.parser.date.MapVal;
import com.hframework.myna.config.domain.model.SemanticParser;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.bn.distribs.CategoricalTable;
import opendial.bn.distribs.IndependentDistribution;
import opendial.bn.values.StringVal;
import opendial.bn.values.Value;
import opendial.modules.Module;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserUtterance2ActionModules extends AbstractModule implements Module {
    private static final Logger logger = LoggerFactory.getLogger(UserUtterance2ActionModules.class);

    public UserUtterance2ActionModules(DialogueSystem system) {
        super(system);
    }


    @Override
    public void trigger(DialogueState state, Collection<String> updatedVars) {
        if (updatedVars.contains("u_u") && state.hasChanceNode("u_u")) {
            QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(state);
            if(currentQnaDescriptor == null) return;
            IndependentDistribution userUtterance = state.queryProb("u_u");
            Map<Value, Double> inputTable = userUtterance.toDiscrete().getTable();

            Map<String, Map<Value, Double>> actionTable = new HashMap<>();// Map<卡槽编码，Map<Action,概率>>

            //FAQ前置切换 是否有值
            if(getCurrentTaskNode(state).getObject().getFaqPattern() == (byte)0) {
                //调用任务知识库进行进一步作答分析
                String answer = FAQRepository.search(getVariableBestValue(state, "u_u"));
                if(StringUtils.isNotBlank(answer)) {
                    return;
                }
            }

            boolean backwardReasoningUsable = userActionParse(inputTable, actionTable, currentQnaDescriptor, state);

            if(backwardReasoningUsable) {
                DialogSystemHelper.intoBackwardReasoningStage(system);
//                system.addContent("backward_reasoning", actionTable.size());
                for (String slotCode : actionTable.keySet()) {
                    if(!actionTable.isEmpty()) {
                        CategoricalTable.Builder builder = new CategoricalTable.Builder("a_u");
                        builder.addRows(actionTable.get(slotCode));
                        system.addContent("current_step", slotCode);
                        system.addContent(builder.build());
                    }
                }
                DialogSystemHelper.breakBackwardReasoningStage(system);
            }else {
                for (String slotCode : actionTable.keySet()) {
                    if(!actionTable.isEmpty()) {
                        CategoricalTable.Builder builder = new CategoricalTable.Builder("a_u");
                        builder.addRows(actionTable.get(slotCode));
//                        System.out.println("77777 =>" + builder.build());
                        system.addContent(builder.build());
                    }
                }
            }
        }
    }

    public boolean userActionParse(Map<Value, Double> inputTable, Map<String, Map<Value, Double>> actionTable,
                                   QnaDescriptor currentQnaDescriptor, DialogueState state) {
        boolean backwardReasoningUsable = false;
        for (Map.Entry<Value, Double> entry : inputTable.entrySet()) {
            Value value = entry.getKey();
            Double prob = entry.getValue();
            Value newValue = null;
            CorpusMatchSemanticParser preParser = ServiceFactory.getService(CorpusMatchSemanticParser.class);
            MapVal<String, String> mapVal = (MapVal<String, String>)preParser.parse(value, currentQnaDescriptor, state);
            if(mapVal != null && mapVal.length() > 0) {
                String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
                for (Map.Entry<String, String> entry2 : mapVal.getMap().entrySet()) {
                    String slotCode = entry2.getKey();
                    String slotValue = entry2.getValue();
                    newValue = semanticParse(getCurrentQnaDescriptor(taskCode, slotCode), new StringVal(slotValue), state);
                    addUserActionTableIfExists(actionTable, slotCode, newValue, prob);
                }
                backwardReasoningUsable = true;
            }else {
                newValue = semanticParse(currentQnaDescriptor, value, state);
//                System.out.println("6666666666=>"  + newValue + "|" + currentQnaDescriptor.getCode() + "|" + prob);
                addUserActionTableIfExists(actionTable, currentQnaDescriptor.getCode(), newValue, prob);
            }
        }
        return backwardReasoningUsable;
    }

    private void addUserActionTableIfExists(Map<String, Map<Value, Double>> actionTable, String code, Value newValue, Double prob) {
        if(newValue == null) return ;

        if(!actionTable.containsKey(code)) actionTable.put(code, new HashMap<Value, Double>());

        newValue = new StringVal("Inform("+ code +"," + newValue + ")");

        Map<Value, Double> tempMap = actionTable.get(code);
        if(tempMap.containsKey(newValue)) {
            tempMap.put(newValue, prob + tempMap.get(newValue));
        }else {
            tempMap.put(newValue, prob);
        }
    }

    private Value semanticParse(QnaDescriptor currentQnaDescriptor, Value value, DialogueState state) {
        Value newValue = null;
        for (SemanticParser semanticParser : currentQnaDescriptor.getSemanticParsers()) {
            String parseAddr = semanticParser.getParseAddr();
            if(ConfigurationEnums.SemanticParserMethod.Local.value == semanticParser.getParseMethod()) {
                try {
                    ISemanticParser parser = (ISemanticParser) ServiceFactory.getService(Class.forName(parseAddr.trim()));
                    newValue = parser.parse(value, currentQnaDescriptor, state);
                    if(newValue != null) break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(ConfigurationEnums.SemanticParserMethod.Http.value == semanticParser.getParseMethod()) {
                //TODO
            }
        }
        return newValue;
    }
}
