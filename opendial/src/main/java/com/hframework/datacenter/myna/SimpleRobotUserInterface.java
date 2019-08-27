package com.hframework.datacenter.myna;

import opendial.DialogueSystem;

import java.util.List;
import java.util.Scanner;

public class SimpleRobotUserInterface extends RobotUserInterface {

    public SimpleRobotUserInterface(DialogueSystem system) {
        super(system);
    }

    public SimpleRobotUserInterface(DialogueSystem system, boolean isSync) {
        super(system, isSync);
    }

    @Override
    public void start() {
        super.start();

        //这里必须放在线程里面执行，否则DialogueSystem不能完全启动，init变量无法初始化
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    if(isSync) {
                        output(pollAllOutputData());
                    }
                }
                catch (InterruptedException e) {
                }
                while (true) {
//                    System.out.println("Type new input: ");
                    String input = new Scanner(System.in).nextLine();
                    input(input);
                    if(isSync) {
                        output(pollAllOutputData());
                    }

                }
            }
        }).start();
    }

    @Override
    public void output(List<RobotData> robotDatas) {
        for (RobotData robotData : robotDatas) {
            System.out.println(robotData.getReturnVal());
        }

    }


}
