package com.hframework.datacenter.myna;

import org.junit.Test;

import java.util.List;

public class RobotSuite {


    @Test
    public void SimpleUI() {
        Robot robot = RobotManager.createRobot("flightbooking");
        robot.attachSimpleUI();
        robot.start();
    }

    @Test
    public void connection(){
        final Robot robot = RobotManager.createSyncUIRobot("flightbooking");
        robot.start();
        RobotUserInterface userInterface = robot.getUserInterface();
        final List<RobotData> outputData = userInterface.pollAllOutputData();
        System.out.println(outputData);
    }

    public static void main(String[] args) {
        Robot robot = RobotManager.createRobot("flightbooking");
        robot.attachSimpleUI();
        robot.start();
    }

}
