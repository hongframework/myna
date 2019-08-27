package com.hframework.datacenter.myna;

import opendial.DialogueSystem;

import java.util.List;
import java.util.Set;

public class ASyncRobotUserInterface extends RobotUserInterface {

    private ASyncNotification aSyncNotification;

    public ASyncRobotUserInterface(DialogueSystem system, ASyncNotification aSyncNotification) {
        super(system);
        this.aSyncNotification = aSyncNotification;
    }

    @Override
    public Set<String> input(String rawText) {
        return super.input(rawText);
    }

    @Override
    public Set<String> input(InputData data) {
        return super.input(data);
    }

    @Override
    public void output(List<RobotData> robotData) {
        aSyncNotification.notification(robotData);
    }


}
