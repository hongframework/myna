package com.hframework.datacenter.myna.modules;

import com.google.common.collect.Lists;
import com.hframework.common.monitor.Node;
import com.hframework.datacenter.myna.RobotManager;
import com.hframework.datacenter.myna.descriptor.ConfigurationDescriptor;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.datacenter.myna.parser.date.util.StringUtil;
import com.hframework.myna.config.domain.model.Qna;
import com.hframework.myna.config.domain.model.SemanticParser;
import com.hframework.myna.config.domain.model.Task;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.bn.nodes.ChanceNode;
import opendial.modules.Module;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public abstract class AbstractModule implements Module {
    protected DialogueSystem system;

    public AbstractModule(DialogueSystem system) {
        this.system = system;
    }

    @Override
    public void start() {

    }

    public Node<Task> getCurrentTaskNode(DialogueState state) {
        String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
        Collection<Node> allTasks = RobotManager.getNodeNetwork().get(Task.class).values();
        for (Node taskNode : allTasks) {
            Task task = (Task) taskNode.getObject();
            if(taskCode.equals(task.getCode())){
                return taskNode;
            }
        }
        return null;
    }

    public Node<Qna> getCurrentQnaNode(DialogueState state) {
        String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
        String currentStep = state.queryProb("current_step").getBest().toString();
        Collection<Node> allQnas = RobotManager.getNodeNetwork().get(Qna.class).values();
        for (Node qnaNode : allQnas) {
            Qna qna = (Qna) qnaNode.getObject();
            Task task = (Task) qnaNode.getInput(Task.class).getObject();
            if(currentStep.equals(qna.getKeyword()) && taskCode.equals(task.getCode())){
                return qnaNode;
            }
        }
        return null;
    }

    public Set<String> getSlot(DialogueState state) {
        Set<String> result = new LinkedHashSet<>();
        String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
        ConfigurationDescriptor configurationDescriptor = RobotManager.getConfigurationDescriptor(taskCode);
        for (QnaDescriptor qnaDescriptor : configurationDescriptor.getQnas()) {
            result.add(qnaDescriptor.getCode());
        }
        return result;
    }

    public Map<String, Object> getSlotValue(DialogueState state) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (String slot : getSlot(state)) {
            if(state.hasChanceNode(slot)){
                result.put(slot, state.getChanceNode(slot).getValues().iterator().next());
            }
        }
        return result;
    }

    public QnaDescriptor getCurrentQnaDescriptor(DialogueState state) {
        String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
        String currentStep = state.queryProb("current_step").getBest().toString();
        return getCurrentQnaDescriptor(taskCode, currentStep);
    }

    public String getVariableBestValue(DialogueState state, String value) {
        if(state.hasNode(value)) {
            return state.queryProb(value).getBest().toString();
        }else {
            return null;
        }
    }

    public QnaDescriptor getCurrentQnaDescriptor(String taskCode, String currentStep) {
        ConfigurationDescriptor configurationDescriptor = RobotManager.getConfigurationDescriptor(taskCode);
        for (QnaDescriptor qnaDescriptor : configurationDescriptor.getQnas()) {
            if(currentStep.equals(qnaDescriptor.getCode())) {
                return qnaDescriptor;
            }
        }

        if("Final".equals(currentStep)) {
            QnaDescriptor qnaDescriptor = new QnaDescriptor();
            qnaDescriptor.setCode("Final");
            List<SemanticParser> result = new ArrayList<>();
            for (Node node : RobotManager.getNodeNetwork().get(SemanticParser.class).values()) {
                if(node.getObject() != null && ((SemanticParser)node.getObject()).getParseType() != null
                        &&((SemanticParser)node.getObject()).getParseType() == (byte)1) {
                    result.add((SemanticParser) node.getObject());
                }
            }
            qnaDescriptor.setSemanticParsers(result);
            return qnaDescriptor;
        }

        return null;
    }


    @Override
    public void pause(boolean b) {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

}
