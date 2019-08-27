package com.hframework.datacenter.myna;

import opendial.DialogueSystem;

import java.util.List;

public class SyncRobotUserInterface extends RobotUserInterface {

    public SyncRobotUserInterface(DialogueSystem system) {
        super(system, true);
    }
    public List<RobotData> interactive(String rawText){
        super.input(rawText);
        return pollAllOutputData();
    }

    public List<RobotData> interactive(InputData data){
        super.input(data);
        return pollAllOutputData();
    }

    @Override
    public void output(List<RobotData> robotData) {
        throw new RuntimeException("synchronize interactive can not invoke!");
    }


}
