package com.hframework.datacenter.myna.modules;

import opendial.DialogueState;
import opendial.DialogueSystem;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DialogSystemHelper {

    public static final String TASK_CODE = "task_code";//任务编码

    public static final String P2P_REPLY = "p2p_reply"; //点对对应答阶段
    public static final String TIME_OUT = "time_out"; //进入超时状态
    public static final String BREAK_STEPS = "break_steps"; //跳过问答的阶段
    public static final String BACKWARD_REASONING = "backward_reasoning"; //反向推断（通过模板匹配一个或多个卡槽）
    public static final String PREDICT_CURRENT_STEP = "predict_current_step";//预计的下一各阶段

    public static final String BUSINESS_HANDLE = "business_handle"; //业务处理
    public static final String BUSINESS_HANDLE_DOWN = "business_handle_down"; //业务处理结束




    public static void intoBackwardReasoningStage(DialogueSystem system) {
        system.addContent(BACKWARD_REASONING, "true");
    }

    public static void breakBackwardReasoningStage(DialogueSystem system) {
        system.removeContent(BACKWARD_REASONING);
    }

    public static boolean isBackwardReasoningStage(DialogueSystem system) {
        return system.getState().hasNode(BACKWARD_REASONING);
    }

    public static void intoP2pReplyStage(DialogueSystem system) {
        system.addContent(P2P_REPLY, "true");
    }

    public static void breakP2pReplyStage(DialogueSystem system) {
        system.removeContent(P2P_REPLY);
    }

    public static boolean isP2pReplyStage(DialogueSystem system) {
        return system.getState().hasNode(P2P_REPLY);
    }

    public static void intoTimeOutStatus(DialogueSystem system) {
        system.addContent(TIME_OUT, "true");
    }

    public static void breakTimeOuStatus(DialogueSystem system) {
        system.removeContent(TIME_OUT);
    }

    public static void addBreakStep(DialogueSystem system, String step) {
        String breakSteps = getVariableBestValue(system.getState(), BREAK_STEPS);
        if(breakSteps == null) {
            breakSteps = step;
        }else {
            breakSteps += ("|" + step);
        }
        system.addContent(BREAK_STEPS, breakSteps);
    }

    public static Set<String> getBreakSteps(DialogueState state) {
        String breakSteps = getVariableBestValue(state, BREAK_STEPS);
        if(StringUtils.isBlank(breakSteps)) {
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        result.addAll(Arrays.asList(breakSteps.split("\\|")));
        return result;
    }


    public static String getVariableBestValue(DialogueState state, String value) {
        if(state.hasNode(value)) {
            return state.queryProb(value).getBest().toString();
        }else {
            return null;
        }
    }
}
