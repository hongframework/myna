package com.hframework.datacenter.myna.modules;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hframework.datacenter.myna.RobotManager;
import com.hframework.datacenter.myna.descriptor.ConfigurationDescriptor;
import com.hframework.datacenter.myna.descriptor.JudgmentBranchDescriptor;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.modules.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MachineGround2NextStepModules extends AbstractModule implements Module {
    private static final Logger logger = LoggerFactory.getLogger(MachineGround2NextStepModules.class);
    public MachineGround2NextStepModules(DialogueSystem system) {
        super(system);
    }

    @Override
    public void trigger(DialogueState state, Collection<String> updatedVars) {
        boolean flag = false;
        //反向推理结束时
        if (updatedVars.contains(DialogSystemHelper.BACKWARD_REASONING) && !DialogSystemHelper.isBackwardReasoningStage(system)) {
            flag = true;
        }else if(!DialogSystemHelper.isBackwardReasoningStage(system) &&
                updatedVars.contains(DialogSystemHelper.PREDICT_CURRENT_STEP) &&
                state.hasChanceNode(DialogSystemHelper.PREDICT_CURRENT_STEP)){
            flag = true;
        }
        if(flag) {
            List<String> allSlots = Lists.newArrayList(getSlot(state));
            Map<String, Object> slotValueInfo = getSlotValue(state);
            Set<String> breakSteps = DialogSystemHelper.getBreakSteps(state);

//            allSlots.removeAll(slotValueInfo.keySet());
            if(allSlots.size() == 0) {
                return;
            }
            String firstSlotCode = allSlots.get(0);
            boolean headSlotFilled = slotValueInfo.containsKey(firstSlotCode) || breakSteps.contains(firstSlotCode);

            String taskCode = state.queryProb(DialogSystemHelper.TASK_CODE).getBest().toString();
            String nextSlotCode = null;
            if(headSlotFilled) {
                while (true) {
                    QnaDescriptor currentQnaDescriptor = getCurrentQnaDescriptor(taskCode, firstSlotCode);
                    if(currentQnaDescriptor.isJudgmentQna()) {
                        if(breakSteps.contains(firstSlotCode)){//跳过的分支问答
                            for (JudgmentBranchDescriptor judgmentBranchDescriptor : currentQnaDescriptor.getBranchs()) {
                                if(judgmentBranchDescriptor.getValue().equals("false")){
                                    nextSlotCode = judgmentBranchDescriptor.getNext();
                                    break;
                                }
                            }
                        }else {
                            for (JudgmentBranchDescriptor judgmentBranchDescriptor : currentQnaDescriptor.getBranchs()) {
                                if(judgmentBranchDescriptor.getValue().equals(String.valueOf(slotValueInfo.get(firstSlotCode)))){
                                    nextSlotCode = judgmentBranchDescriptor.getNext();
                                    break;
                                }
                            }
                        }

                        for (JudgmentBranchDescriptor judgmentBranchDescriptor : currentQnaDescriptor.getBranchs()) {
                            if(judgmentBranchDescriptor.getValue().equals(String.valueOf(slotValueInfo.get(firstSlotCode)))){
                                nextSlotCode = judgmentBranchDescriptor.getNext();
                                break;
                            }
                        }
                    }else {
                        nextSlotCode = currentQnaDescriptor.getNext();
                    }
                    if(slotValueInfo.containsKey(nextSlotCode) || breakSteps.contains(nextSlotCode)) {
                        firstSlotCode = nextSlotCode;
                    }else {
                        break;
                    }
                }
            }else {
                nextSlotCode = allSlots.get(0);
            }
            system.addContent("current_step", nextSlotCode);//按照顺序设置下一各空槽

//            if(!allSlots.isEmpty()) {
//                String nextStep = allSlots.iterator().next();
//                system.addContent("current_step", nextStep);//按照顺序设置下一各空槽
//            }else {
//                system.addContent("current_step", "Final");
//            }
        }
    }
}
