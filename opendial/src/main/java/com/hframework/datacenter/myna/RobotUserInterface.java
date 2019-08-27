package com.hframework.datacenter.myna;

import com.google.common.collect.Lists;
import opendial.DialogueState;
import opendial.DialogueSystem;
import opendial.bn.distribs.CategoricalTable;
import opendial.bn.distribs.IndependentDistribution;
import opendial.bn.values.NoneVal;
import opendial.bn.values.Value;
import opendial.modules.Module;
import opendial.utils.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class RobotUserInterface implements Module {
    private static final Logger logger = LoggerFactory.getLogger(RobotUserInterface.class);
    boolean paused = true;

    DialogueSystem system;

    private Thread outputThread;

    protected boolean isSync = false;//是否为同步交互，默认为异步

    private LinkedBlockingQueue<RobotData> outputQueue = new LinkedBlockingQueue();

    public RobotUserInterface(DialogueSystem system) {
        this(system, false);
    }

    public RobotUserInterface(DialogueSystem system, boolean isSync) {
        this.system = system;
        this.isSync = isSync;
    }

    @Override
    public void start() {
        paused = false;
        logger.info("Starting Robot User Interface...");
        if(!isSync) {
            outputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    RobotData robotData = null;
                    while (true) {
                        try {
                            robotData = outputQueue.take();
                        } catch (InterruptedException e) {
                            break;
                        }
                        logger.info("output data : {}", robotData);
                        output(Lists.newArrayList(robotData));
                        logger.info("output data : {}({}) is ok !", robotData.getReturnVal(), robotData.getReturnProb());
                    }
                }
            });
            outputThread.start();
        }
    }

    public synchronized List<RobotData> pollAllOutputData(){
        List<RobotData> result = new ArrayList<>();
        RobotData poll = outputQueue.poll();
        while (poll != null) {
            result.add(poll);
            poll = outputQueue.poll();
        }
        return result;
    }

    @Override
    public void trigger(DialogueState state, Collection<String> updatedVars) {
        String maVar = system.getSettings().systemOutput;
        if (!paused && updatedVars.contains(maVar) && state.hasChanceNode(maVar)) {
            CategoricalTable probDistributionTable = system.getContent(maVar).toDiscrete();
            Value bestValue = probDistributionTable.getBest();
            if (!(bestValue instanceof NoneVal)) {
                String returnVal = bestValue.toString();
                String returnProb = StringUtils.getShortForm(probDistributionTable.getProb(bestValue));
                logger.info("trigger : {}({})", returnVal, returnProb);
                if(StringUtil.isBlank(returnVal)){
                   return;
                }
                outputQueue.offer(new RobotData(returnVal, returnProb, probDistributionTable));
            }
        }
    }

    public abstract void output(List<RobotData> robotDatas);


    public InputData parseInputData(String rawText) {
        Map<String, Double> table = StringUtils.getTableFromInput(rawText);
        //TODO 如下内容是否有必须开启新的线程，异步后续逻辑处理
        String var = (!system.getSettings().invertedRole) ? system.getSettings().userInput
                : system.getSettings().systemOutput;
        CategoricalTable.Builder builder = new CategoricalTable.Builder(var);
        for (String input : table.keySet()) {
            builder.addRow(input, table.get(input));
        }
        return new InputData(builder.build());
    }

    public Set<String> input(String rawText) {
        return input(parseInputData(rawText));
    }

    public Set<String> input(InputData data) {
        return system.addContent(data.getTable());
    }

    @Override
    public void pause(boolean toPause) {
        paused = toPause;
    }

    @Override
    public boolean isRunning() {
        return !paused;
    }

    public static class InputData{
        IndependentDistribution table;

        public InputData(IndependentDistribution table) {
            this.table = table;
        }

        public IndependentDistribution getTable() {
            return table;
        }

        @Override
        public String toString() {
            return table.toString();
        }
    }




}
