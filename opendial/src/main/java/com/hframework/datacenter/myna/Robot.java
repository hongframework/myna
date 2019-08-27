package com.hframework.datacenter.myna;

import com.google.common.collect.Lists;
import com.hframework.common.util.CommonUtils;
import com.hframework.common.util.DateUtils;
import com.hframework.common.util.file.FileUtils;
import com.hframework.common.util.message.PropertyReader;
import opendial.DialogueSystem;
import opendial.Settings;
import opendial.domains.Domain;
import opendial.modules.DialogueRecorder;
import opendial.modules.Module;
import opendial.modules.StatePruner;
import opendial.readers.XMLDomainReader;
import opendial.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Robot {
    private static final Logger logger = LoggerFactory.getLogger(Robot.class);

    public static final String DIALOG_LOGS_PATH = "dialog.logs.path";
    public static final String DIALOG_MODELS_PATH = "dialog.models.path";
    public static final String FAQ_DATA_PATH = "faq.data.path";
    public static final String FAQ_INDEX_PATH = "faq.index.path";
    public static final String SYNTAX_PARSE_EXAMPLE = "syntax.parse.example";

    public static PropertyReader pr = PropertyReader.read("hframe.properties")
            .addDefine(DIALOG_LOGS_PATH, DIALOG_MODELS_PATH, FAQ_DATA_PATH, FAQ_INDEX_PATH, SYNTAX_PARSE_EXAMPLE);

    public static final String domainsPath;
    public static final String dialogLogPath;

    public static final String faqDataPath;
    public static final String faqIndexPath;

    public static final String syntaxParsePath;

    static {
        domainsPath = pr.get(DIALOG_MODELS_PATH, Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/opendial/domains") + "/";
        dialogLogPath = pr.get(DIALOG_LOGS_PATH, domainsPath + "dialog");

        faqDataPath = pr.get(FAQ_DATA_PATH, domainsPath + "search/data");
        faqIndexPath = pr.get(FAQ_INDEX_PATH, domainsPath + "search/index");
        syntaxParsePath = pr.get(SYNTAX_PARSE_EXAMPLE, domainsPath + "syntax");
    }

    private DialogueSystem system;

    private RobotUserInterface userInterface;

    private String robotCode;
    private String sessionId;
    private long lastChatTime;
    private long createTime;


    public Robot(String robotCode, SyncMode syncMode, ASyncNotification notification){
        this(robotCode);
        if(SyncMode.Sync.equals(syncMode)) {
            attachSyncUI();
        }else {
            attachASyncUI(notification);
        }
    }

    public Robot(String robotCode){
        /**
         * 先验概率选项多时,可能所有的先验概率都比较低，如果这里不调整小一下，那么都会别减枝掉，见RuleOutput.java
         */
        StatePruner.VALUE_PRUNING_THRESHOLD =  0.001;
        String filePath = domainsPath + robotCode + ".xml";
        if(!new File(filePath).exists()) {
            logger.error("robot config xml path => {}", filePath);
            throw new RuntimeException(robotCode + "'s robot is exists !");
        }
        system = new DialogueSystem();
        system.getSettings().fillSettings(System.getProperties());
        Settings settings = system.getSettings();
        settings.showGUI = false;
        system.changeSettings(settings);
        sessionId = CommonUtils.uuid();
        this.robotCode = robotCode;
//        if(!"flightbooking".equals(robotCode)) {
//            throw new RuntimeException(robotCode + "'s robot is exists !");
//        }
    }


    public void attachModule(Module module) {
        system.attachModule(module);
    }

    public <T extends RobotUserInterface> void attachUserInterface(RobotUserInterface module) {
        userInterface = module;
        attachModule(module);
    }

    public <T extends Module> void attachModule(Class<T> module) {
        system.attachModule(module);
    }

    public void start(){
        createTime = System.currentTimeMillis();
        lastChatTime = System.currentTimeMillis();
        String filePath = domainsPath + robotCode + ".xml";
        if(new File(filePath).exists()) {
            loadDomain(filePath);
        }

        system.startSystem();
        logger.info("Dialogue system started!");
    }
    public void saveChat() throws IOException {
        //DateUtils.getCurrentDate("yyyy_MM_dd_HH_mm_ss")
        String fileName =  DateUtils.getDate(new Date(createTime), "yyyy_MM_dd_HH_mm_ss")+ "_" + sessionId + ".xml";
        String filePath = dialogLogPath + "/" + robotCode + "/" + fileName;
        FileUtils.newFile(filePath);
        system.getModule(DialogueRecorder.class).writeToFile(filePath);
        Document xmlDocument = XMLUtils.getXMLDocument(filePath);
        NodeList nodeList = xmlDocument.getDocumentElement().getChildNodes();
        int userInput = 0;
        int unknownInput = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if("systemTurn".equals(item.getNodeName())) {
                NodeList attrNodes = item.getChildNodes();
                boolean isAskRepeat = false;
                boolean isTimeOut = false;
                boolean isP2pReply = false;
                for (int j = 0; j < attrNodes.getLength(); j++) {
                    Node variableNode = attrNodes.item(j);
                    if(!variableNode.hasAttributes()) {
                        continue;
                    }
                    if("a_m".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                        if(variableNode.getChildNodes().item(1).getChildNodes().getLength() == 0) {
                            continue;
                        }
                        String  ma = variableNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
                        isAskRepeat |= "AskRepeat".equals(ma);
                    }

                    if("time_out".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                        isTimeOut = true;
                    }
                    if("p2p_reply".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                        isP2pReply = true;
                    }
                }
                if(isAskRepeat && !isTimeOut && !isP2pReply) {
                    unknownInput ++;
                }
            }else if("userTurn".equals(item.getNodeName())) {
                userInput ++;
            }
        }
        String newPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".";
        if(unknownInput > 0 && userInput > 0 ){
            newPath += (((userInput - unknownInput) * 100) / userInput + ".check.xml");
        }else{
            newPath += (100 + ".ok.xml");
        }
        File[] fileList = FileUtils.getFileList(new File(dialogLogPath + "/" + robotCode));
        for (File file : fileList) {
            System.out.println(file.getName() + " => " + fileName);
            if(file.getName().startsWith(fileName.substring(0, fileName.lastIndexOf(".") + 1)) && !fileName.equals(file.getName())) {
                System.out.println(file.getName() + "delete");
                FileUtils.deleteFile(file.getPath());
            }
        }
        FileUtils.moveFile(filePath, newPath);
    }

    public void loadDomain(String domainFile){
        Domain domain = XMLDomainReader.extractDomain(domainFile);
        system.changeDomain(domain);
    }

    public DialogueSystem getSystem() {
        return system;
    }

    public RobotUserInterface attachSimpleUI() {
        SimpleRobotUserInterface ui = new SimpleRobotUserInterface(system, false);
        attachUserInterface(ui);
        return ui;
    }

    public RobotUserInterface attachSyncUI() {
        SyncRobotUserInterface ui = new SyncRobotUserInterface(system);
        attachUserInterface(ui);
        return ui;
    }

    public RobotUserInterface attachASyncUI(ASyncNotification aSyncNotification) {
        ASyncRobotUserInterface ui = new ASyncRobotUserInterface(system, aSyncNotification);
        attachUserInterface(ui);
        return ui;
    }

    public RobotUserInterface getUserInterface() {
        return userInterface;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getLastChatTime() {
        return lastChatTime;
    }

    public void setLastChatTime(long lastChatTime) {
        this.lastChatTime = lastChatTime;
    }

    public long getCreateTime() {
        return createTime;
    }

}
