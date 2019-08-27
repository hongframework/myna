package com.hframework.datacenter.myna;

import com.google.common.collect.Sets;
import com.hframework.common.monitor.Node;
import com.hframework.datacenter.myna.descriptor.ConfigurationDescriptor;
import com.hframework.datacenter.myna.exceptions.TimeOutException;
import com.hframework.myna.config.domain.model.Thesaurus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RobotManager {
    private static Logger logger = LoggerFactory.getLogger(RobotManager.class);

    public static Map<String, Robot> robots = new HashMap<>();

    public static BusinessHandlerFactory handlerFactory = BusinessHandlerFactory.getFactory();
    static {
        handlerFactory.register("TicketPriceQuery", new BusinessHandler() {
            @Override
            public Map<String, Object> handle(Map<String, Object> input) {
                return new HashMap(){{
                    put("票价",250);
                }};
            }
        });
        handlerFactory.register("TicketOrderSubmit", new BusinessHandler() {
            @Override
            public Map<String, Object> handle(Map<String, Object> input) {
                return new HashMap(){{
                    put("订单号","999999999999");
                }};
            }
        });
    }

    private static ConfigurationWatcher watcher;

    public static void startWatcher() throws Exception {
        if(watcher == null) {
            watcher = ConfigurationWatcher.getInstance();
            watcher.start();
        }
    }

    public static Map<Class, Map<String, Node>> getNodeNetwork(){
        return watcher.getMonitor().getNodeNetwork();
    }

    public static ConfigurationDescriptor getConfigurationDescriptor(String taskCode){
        return watcher.getConfigurationDescriptors().get(taskCode);
    }

    public static Node<Thesaurus>[] getTnFThesaurus() {
        Map<String, Node> stringNodeMap = watcher.getMonitor().getNodeNetwork().get(Thesaurus.class);
        return new Node[]{stringNodeMap.get("1"), stringNodeMap.get("2")};

    }

    public static Robot createRobot(String robotCode){
        Robot robot = new Robot(robotCode);
        robots.put(robotCode + "-" + robot.getSessionId(), robot);
        return robot;
    }

    public static Robot createSyncUIRobot(String robotCode){
        Robot robot =  new Robot(robotCode, SyncMode.Sync, null);
        robots.put(robotCode + "-" + robot.getSessionId(), robot);
        return robot;
    }

    public static Robot createASyncUIRobot(String robotCode, ASyncNotification notification){
        Robot robot =  new Robot(robotCode, SyncMode.Sync, notification);
        robots.put(robotCode + "-" + robot.getSessionId(), robot);
        return robot;
    }

    public static Robot getRobot(String robotCode, String sessionId) {
        Robot robot = robots.get(robotCode + "-" + sessionId);
        if(robot == null) {
            throw new TimeOutException(robotCode + "_" + sessionId + "'s robot is not exists or expired !");
        }
        return robot;
    }

    public static void removeRobot(String robotCode, String sessionId) throws IOException {
        Robot robot = robots.remove(robotCode + "-" + sessionId);
        robot.saveChat();
    }
    public static void saveRobot(String robotCode, String sessionId) throws IOException {
        Robot robot = robots.get(robotCode + "-" + sessionId);
        robot.saveChat();
    }

    public static BusinessHandler getBusinessHandler(String handleCode) {
        if(!handlerFactory.getHandlerMap().containsKey(handleCode)) {
            throw new RuntimeException("handle not exists !");
        }
        return handlerFactory.getHandlerMap().get(handleCode);
    }

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(10000L);
                        for (String key : Sets.newHashSet(robots.keySet())) {
                            Robot robot = robots.get(key);
                            if(robot.getLastChatTime() < System.currentTimeMillis() - 10 * 60 * 1000) {
                                robot.saveChat();
                                robots.remove(key);

                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
