package com.hframework.datacenter.myna;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.beans.exceptions.BusinessException;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.util.DateUtils;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.collect.bean.Mapper;
import com.hframework.common.util.file.FileUtils;
import com.hframework.web.ControllerHelper;
import com.hframework.web.controller.DefaultController;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.datacenter.myna.exceptions.TimeOutException;
import com.hframework.datacenter.myna.modules.UserUtterance2ActionModules;
import com.hframework.myna.config.domain.model.*;
import com.hframework.myna.config.service.interfaces.*;
import opendial.DialogueState;
import opendial.bn.values.Value;
import opendial.utils.XMLUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/robot")
public class MynaRobotController {
    private static final Logger logger = LoggerFactory.getLogger(MynaRobotController.class);
    @Resource
    private ICorpusSV iCorpusSV;
    @Resource
    private IThesaurusSV thesaurusSV;
    @Resource
    private IWordsSV iWordsSV;
    @Resource
    private ITaskSV iTaskSV;
    @Resource
    private IQnaSV iQnaSV;
    @Resource
    private IOptionsSV optionsSV;
    @Resource
    private IOptionSV optionSV;
    @Resource
    private IP2pReplySV p2pReplySV;
    @Resource
    private IP2pCorpusSV corpusSV;


    private static Map<String, Robot> defaultRobot = new HashMap<>();
    /**
     * 进入历史聊天页面
     * @return
     */
    @RequestMapping(value = "/chat/history/all.html")
    @ResponseBody
    public ModelAndView chatAllHistoryPage(HttpServletRequest request,
                                        HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        try {
            RobotManager.startWatcher();
            mav = ServiceFactory.getService(DefaultController.class).root(request, response);
            mav.setViewName("chat/history");

            JSONArray category = new JSONArray();
            File[] robotList = FileUtils.getFileList(new File(Robot.dialogLogPath));
            if(robotList != null) {
                for (File robotFile : robotList) {
                    final String robotName = robotFile.getName();
                    File[] fileList = FileUtils.getFileList(robotFile);
                    if(fileList != null) {
                        final JSONArray files = new JSONArray();
                        for (final File file : fileList) {
                            String fileName = file.getName();
                            String closeTime = fileName.substring(0, fileName.lastIndexOf("_"));
                            String sessionId = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.indexOf("."));
                            String meta = fileName.substring(fileName.indexOf(".") + 1, fileName.lastIndexOf("."));
                            final boolean isCheck = !"ok".equals(meta.substring(meta.indexOf(".") + 1));

                            final String title = "[" + DateUtils.getDate(DateUtils.parse(closeTime, "yyyy_MM_dd_HH_mm_ss")
                                    ,"yyyy-MM-dd HH:mm:ss") + "]" + sessionId;
                            files.add(new JSONObject(new HashMap(){{
                                put("title", title);
                                put("robot", robotName);
                                put("file", file.getName());
                                put("isCheck", isCheck);

                            }}));
                        }
                        category.add(new JSONObject(new HashMap(){{
                            put("title", robotName);
                            put("files", files);

                        }}));
                    }
                }
            }
            mav.addObject("category", category);


        } catch (Throwable e) {
            logger.error("error : {}", e);
        }

        return mav;
    }

    /**
     * 进入历史聊天页面
     * @return
     */
    @RequestMapping(value = "/chat/history.html")
    @ResponseBody
    public ModelAndView chatHistoryPage(HttpServletRequest request,
                                 HttpServletResponse response) {
        ModelAndView mav = chatAllHistoryPage(request, response);
        JSONArray category = (JSONArray) mav.getModel().get("category");
        for (Object o : category) {
            JSONArray files = ((JSONObject) o).getJSONArray("files");
            Iterator<Object> objectListIterator = files.iterator();
            while (objectListIterator.hasNext()) {
                JSONObject file = (JSONObject) objectListIterator.next();
                if(!file.getBoolean("isCheck")) {
//                    files.fluentRemove(file);
                    objectListIterator.remove();
                }
            }
        }
        return mav;
    }

    /**
     * 创建词汇
     * @param name
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/save/words.json")
    @ResponseBody
    public ResultData saveWords(String robot, String thesaurus, String name) {
        logger.debug("request : {}", robot, thesaurus, name);
        try {
            if(StringUtils.isBlank(robot)) {
                return ResultData.error(ResultCode.get("111","请选择聊天记录！"));
            }

            Task_Example taskExample = new Task_Example();
            taskExample.createCriteria().andCodeEqualTo(robot);
            List<Task> taskListByExample = iTaskSV.getTaskListByExample(taskExample);
            if(taskListByExample.isEmpty()) {
                return ResultData.error(ResultCode.get("111","机器人对应任务不存在！"));
            }
            Long taskId = taskListByExample.get(0).getId();

            Thesaurus_Example thesaurusExample = new Thesaurus_Example();
            thesaurusExample.createCriteria().andNameEqualTo(thesaurus.substring(thesaurus.indexOf("-") + 1).trim());
            List<Thesaurus> thesaurusList = thesaurusSV.getThesaurusListByExample(thesaurusExample);
            Thesaurus thesaurus1 = null;
            if(!thesaurusList.isEmpty()) {
                thesaurus1 = thesaurusList.get(0);
            }else {
                thesaurus1 = new Thesaurus();
                thesaurus1.setName(thesaurus.substring(thesaurus.indexOf("-") + 1).trim());
                thesaurus1.setType((byte)1);
                thesaurus1.setStatus((byte)1);
                ControllerHelper.setDefaultValue(thesaurus1, "id");
                thesaurusSV.create(thesaurus1);
            }

            Words words = new Words();
            words.setStatus((byte) 1);
            words.setName(name);
            words.setThesaurusId(thesaurus1.getId());
            ControllerHelper.setDefaultValue(words, "id");
            int result = iWordsSV.create(words);
            if(result > 0) {
                return ResultData.success(words);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
     * 创建点对点问答
     * @param robot
     * @param reply
     * @param corpus
     * @return
     */
    @RequestMapping(value = "/save/reply.json")
    @ResponseBody
    public ResultData saveReply(String robot, String reply, String corpus) {
        logger.debug("request : {}", robot, reply, corpus);
        try {
            if(StringUtils.isBlank(robot)) {
                return ResultData.error(ResultCode.get("111","请选择聊天记录！"));
            }

            Task_Example taskExample = new Task_Example();
            taskExample.createCriteria().andCodeEqualTo(robot);
            List<Task> taskListByExample = iTaskSV.getTaskListByExample(taskExample);
            if(taskListByExample.isEmpty()) {
                return ResultData.error(ResultCode.get("111","机器人对应任务不存在！"));
            }
            Long taskId = taskListByExample.get(0).getId();

            P2pReply_Example p2pReplyExample = new P2pReply_Example();
            p2pReplyExample.createCriteria().andAnswerEqualTo(reply.trim());
            List<P2pReply> p2pReplyListByExample = p2pReplySV.getP2pReplyListByExample(p2pReplyExample);
            P2pReply p2pReply = null;
            if(!p2pReplyListByExample.isEmpty()) {
                p2pReply = p2pReplyListByExample.get(0);
            }else {
                p2pReply = new P2pReply();
                p2pReply.setAnswer(reply.trim());
                p2pReply.setType((byte)1);
                p2pReply.setStatus((byte)1);
                p2pReply.setTaskId(taskId);
                ControllerHelper.setDefaultValue(p2pReply, "id");
                p2pReplySV.create(p2pReply);
            }

            P2pCorpus p2pCorpus = new P2pCorpus();
            p2pCorpus.setMatchingType((byte)0);
            p2pCorpus.setCorpuss(corpus.trim());
            p2pCorpus.setP2pRepayId(p2pReply.getId());
            p2pCorpus.setStatus((byte)1);
            ControllerHelper.setDefaultValue(p2pCorpus, "id");
            int result = corpusSV.create(p2pCorpus);
            if(result > 0) {
                return ResultData.success(p2pCorpus);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }


    /**
     * 创建语料库
     * @param content
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/save/corpus.json")
    @ResponseBody
    public ResultData saveCorpus(String content, String robot) {
        logger.debug("request : {}", content, robot);
        try {
            if(StringUtils.isBlank(robot)) {
                return ResultData.error(ResultCode.get("111","请选择聊天记录！"));
            }

            content = content.trim();
            String[] slots = RegexUtils.find(content, "(?<=\\{)[^}]+(?=\\})");

            Corpus corpus = new Corpus();
            Task_Example taskExample = new Task_Example();
            taskExample.createCriteria().andCodeEqualTo(robot);
            List<Task> taskListByExample = iTaskSV.getTaskListByExample(taskExample);
            if(taskListByExample.isEmpty()) {
                return ResultData.error(ResultCode.get("111","机器人对应任务不存在！"));
            }
            Long taskId = taskListByExample.get(0).getId();

            Qna_Example qnaExample = new Qna_Example();
            qnaExample.createCriteria().andTaskIdEqualTo(taskId);
            List<Qna> qnaListByExample = iQnaSV.getQnaListByExample(qnaExample);
            Set<String> slotAllSet = new HashSet<>();
            for (Qna qna : qnaListByExample) {
                slotAllSet.add(qna.getKeyword());
            }

            Set<String> slotSet = Sets.newHashSet(slots);
            slotSet.removeAll(slotAllSet);
            if(!slotSet.isEmpty()) {
                return ResultData.error(ResultCode.get("111","语料中存在无效卡槽！ " + Arrays.toString(slotSet.toArray(new String[0])) + ""));
            }

            if(taskListByExample.size() > 0) {
                corpus.setTaskId(taskId);
                corpus.setContent(content);
                corpus.setSlotNumbers((byte) slots.length);
                corpus.setStatus((byte) 1);
                ControllerHelper.setDefaultValue(corpus, "id");
                int result = iCorpusSV.create(corpus);
                if(result > 0) {
                    return ResultData.success(corpus);
                }
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }


    /**
     * 历史聊天记录
     * @param robot
     * * @param file
     * @return
     */
    @RequestMapping(value = "/chat/history/data.json")
    @ResponseBody
    public ResultData chatHistoryData(@RequestParam("robot") String robot, @RequestParam("file") String file){
        logger.debug("request : {}, {}", robot, file);
        try {
            String filePath =  Robot.dialogLogPath + "/" + robot + "/" + file;
            Document xmlDocument = XMLUtils.getXMLDocument(filePath);
            final JSONArray list = new JSONArray();
            NodeList nodeList = xmlDocument.getDocumentElement().getChildNodes();
            nodes : for (int i = 0; i < nodeList.getLength(); i++) {
                Node item = nodeList.item(i);
                if(!"systemTurn".equals(item.getNodeName()) && !"userTurn".equals(item.getNodeName())) {
                    continue;
                }
                JSONObject jsonObject = new JSONObject();

                if("systemTurn".equals(item.getNodeName())) {
                    jsonObject.put("role","system");
                    NodeList attrNodes = item.getChildNodes();
                    for (int j = 0; j < attrNodes.getLength(); j++) {
                        Node variableNode = attrNodes.item(j);
                        if(!variableNode.hasAttributes()) {
                            continue;
                        }
                        if("u_m".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                            if(variableNode.getChildNodes().item(1).getChildNodes().getLength() == 0) {
                                continue nodes;
                            }
                            jsonObject.put("utterance",variableNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                        }else if("a_m".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                            jsonObject.put("a_m",variableNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
//                        }else if("current_step".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
//                            jsonObject.put("step",variableNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                        }else if("time_out".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                            jsonObject.put("time_out",true);
                        }else if("p2p_reply".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                            jsonObject.put("p2p_reply",true);
                        }
                    }
                }else if("userTurn".equals(item.getNodeName())) {
                    jsonObject.put("role","user");
                    NodeList attrNodes = item.getChildNodes();
                    for (int j = 0; j < attrNodes.getLength(); j++) {
                        Node variableNode = attrNodes.item(j);
                        if(!variableNode.hasAttributes()) {
                            continue;
                        }
                        if("u_u".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                            if(variableNode.getChildNodes().item(1).getChildNodes().getLength() == 0) {
                                jsonObject.put("utterance","");
                            }else {
                                jsonObject.put("utterance",variableNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                            }

                        }else if("current_step".equals(variableNode.getAttributes().getNamedItem("id").getNodeValue())) {
                            jsonObject.put("step",variableNode.getChildNodes().item(1).getChildNodes().item(0).getNodeValue());
                        }
                    }
                }
                list.add(jsonObject);
            }

//            List<String> unknownUtterances = Lists.newArrayList("对不起，您的输入不正确，请重新输入!", "对不起，您的输入我们依然无法识别，请再次输入!");
            int unknownCount = 0;
            if(list.size() > 0) {
                for (int i = list.size() -1 ; i >= 0; i--) {
                    JSONObject item = (JSONObject) list.get(i);
                    if("system".equals(item.getString("role")) && "AskRepeat".equals(item.getString("a_m")) && !item.containsKey("time_out") && !item.containsKey("p2p_reply")) {
                        item.put("is_unknown", true);
                        unknownCount ++ ;
                    }else if("user".equals(item.getString("role")) && unknownCount > 0) {
                        item.put("is_unknown", true);
                        unknownCount --;
                    }
                }
            }


            setUnknownUtteranceNewFlag(robot, list);


            Task_Example taskExample = new Task_Example();
            taskExample.createCriteria().andCodeEqualTo(robot);
            List<Task> taskListByExample = iTaskSV.getTaskListByExample(taskExample);
            if(taskListByExample.isEmpty()) {
                return ResultData.error(ResultCode.get("111","机器人对应任务不存在！"));
            }
            Long taskId = taskListByExample.get(0).getId();

            Qna_Example qnaExample = new Qna_Example();
            qnaExample.createCriteria().andTaskIdEqualTo(taskId);
            List<Qna> qnaListByExample = iQnaSV.getQnaListByExample(qnaExample);
            final Set<String> slotAllSet = new HashSet<>();
            final List<Long> optionIds = new ArrayList<>();
            for (Qna qna : qnaListByExample) {
                slotAllSet.add(qna.getKeyword());
                if(qna.getOptionsId() != null && qna.getOptionsId() > 0) {
                    optionIds.add(qna.getOptionsId());
                }
            }

            final List<String> options = new ArrayList<>();

            Options_Example optionsExample = new Options_Example();
            optionsExample.createCriteria().andIdIn(optionIds);
            List<Options> optionsList = optionsSV.getOptionsListByExample(optionsExample);
            Map<Long, Options> optionsMap = CollectionUtils.convert(optionsList, new Mapper<Long, Options>() {
                @Override
                public <K> K getKey(Options options) {
                    return (K) options.getId();
                }
            });

            Option_Example optionExample = new Option_Example();
            optionExample.createCriteria().andOptionsIdIn(optionIds);
            List<Option> optionList = optionSV.getOptionListByExample(optionExample);
            for (Option option : optionList) {
                for (String optionItem : option.getName().split("[,，、/]+")) {
                    if(com.hframework.common.util.StringUtils.isNotBlank(optionItem)) {
                        options.add(optionsMap.get(option.getOptionsId()).getName() + "-" + optionItem);
                    }
                }

            }
            Collections.sort(options);

            final List<String> replies = new ArrayList<>();
            P2pReply_Example p2pReplyExample = new P2pReply_Example();
            p2pReplyExample.createCriteria().andTaskIdEqualTo(taskId);
            List<P2pReply> p2pReplyList = p2pReplySV.getP2pReplyListByExample(p2pReplyExample);
            for (P2pReply p2pReply : p2pReplyList) {
                replies.add(p2pReply.getAnswer().trim());
            }

//            System.out.println(list.toJSONString());
            return ResultData.success(new HashedMap(){{
                put("list", list);
                put("slots", slotAllSet);
                put("options", options);
                put("replies", replies);
//                put("taskId", finalTaskId);
            }});
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    private void setUnknownUtteranceNewFlag(String robot, JSONArray list) {
        if(!defaultRobot.containsKey(robot)) {
            synchronized (this) {
                if(!defaultRobot.containsKey(robot)) {
                    defaultRobot.put(robot, RobotManager.createRobot(robot));
                    defaultRobot.get(robot).start();
                }
            }
        }
        Robot robot1 = defaultRobot.get(robot);
        SyncRobotUserInterface uuFormat = new SyncRobotUserInterface(robot1.getSystem());
        UserUtterance2ActionModules uu2ua = new UserUtterance2ActionModules(robot1.getSystem());
//        UserUtterance2ActionModules uu2ua = robot1.getSystem().getModule(UserUtterance2ActionModules.class)
        DialogueState state = robot1.getSystem().getState();

        int unknownCount = 0;
        for (Object o : list) {
            JSONObject item = (JSONObject) o;
            if(item.get("is_unknown") != null && item.getBoolean("is_unknown") && "user".equals(item.getString("role"))){
                String utterance = item.getString("utterance");
                String step = item.getString("step");
                robot1.getSystem().addContent("current_step", step);
                QnaDescriptor currentQnaDescriptor = uu2ua.getCurrentQnaDescriptor(state);
                if(currentQnaDescriptor == null) continue;
                Map<String, Map<Value, Double>> actionTable = new HashMap<>();// Map<卡槽编码，Map<Action,概率>>
                RobotUserInterface.InputData inputData = uuFormat.parseInputData(utterance);
                uu2ua.userActionParse(inputData.getTable().toDiscrete().getTable(), actionTable, currentQnaDescriptor, state);
                if(actionTable.size() > 0) {
                    Set<Value> values = new HashSet<>();
                    for (Map<Value, Double> valueDoubleMap : actionTable.values()) {
                        values.addAll(valueDoubleMap.keySet());
                    }
                    List<String> parses = new ArrayList<>();
                    for (Value value : values) {
                        parses.add(value.toString());
                    }
                    item.put("mark_down", true);
                    item.put("new_parses",Arrays.toString(parses.toArray(new String[0])));
                    unknownCount ++;
                }else {
                    String answer = FAQRepository.search(utterance);
                    if(StringUtils.isNotBlank(answer)) {
                        item.put("mark_down", true);
                        item.put("new_parses",new String[]{answer});
                        unknownCount ++;
                    }
                }
            }else if(item.get("is_unknown") != null && item.getBoolean("is_unknown") && unknownCount > 0){
                unknownCount --;
                item.put("mark_down", true);
            }
        }



    }

    /**
     * 历史聊天记录
     * @param robot
     * * @param file
     * @return
     */
    @RequestMapping(value = "/chat/history/sign.json")
    @ResponseBody
    public ResultData chatHistorySign(@RequestParam("robot") String robot, @RequestParam("file") String file){
        logger.debug("request : {}, {}", robot, file);
        try {
            String filePath =  Robot.dialogLogPath + "/" + robot + "/" + file;
            FileUtils.moveFile(filePath, filePath.replace(".check.", ".ok."));
            return ResultData.success(new HashedMap(){{
            }});
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }


    /**
     * 进入聊天室
     * @return
     */
    @RequestMapping(value = "/chat/room.html")
    @ResponseBody
    public ModelAndView chatRoom(HttpServletRequest request,
                             HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        IRobotSV robotSV = ServiceFactory.getService(IRobotSV.class);
        Robot_Example example = new Robot_Example();
        example.createCriteria().andStatusEqualTo((byte)1);
        try {
            List<com.hframework.myna.config.domain.model.Robot> robots = robotSV.getRobotListByExample(example);
            mav.addObject("robots", robots);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mav.setViewName("chat/chatroom");
        return mav;
    }

    /**
     * 建立聊天会话
     * @param robotCode
     * @return
     */
    @RequestMapping(value = "/conn/{robotCode}.json")
    @ResponseBody
    public ResultData connection(@PathVariable("robotCode") String robotCode){
        logger.debug("request : {}", robotCode);
        try {
            RobotManager.startWatcher();
            final Robot robot = RobotManager.createSyncUIRobot(robotCode);
            robot.start();
            RobotUserInterface userInterface = robot.getUserInterface();
            final List<RobotData> outputData = userInterface.pollAllOutputData();

            return ResultData.success(new HashedMap(){{
                put("utterance", CollectionUtils.fetch(outputData, new Fetcher<RobotData, String>() {
                    @Override
                    public String fetch(RobotData robotData) {
                        return robotData.getReturnVal();
                    }
                }));
                put("sessionId", robot.getSessionId());
            }});
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 聊天对话
     * @param robotCode
     * @param sessionId
     * @param utterance
     * @return
     */
    @RequestMapping(value = "/chat/{robotCode}/{sessionId}.json")
    @ResponseBody
    public ResultData chat(@PathVariable("robotCode") String robotCode,
                           @PathVariable("sessionId") String sessionId,
                           @ModelAttribute("utterance") String utterance){
        logger.debug("request : {},{},{}", robotCode, sessionId, utterance);
        try{
            final Robot robot = RobotManager.getRobot(robotCode, sessionId);
            robot.setLastChatTime(System.currentTimeMillis());
            SyncRobotUserInterface ui = (SyncRobotUserInterface)robot.getUserInterface();
            final List<RobotData> outputData = ui.interactive(utterance);
            return ResultData.success(new HashedMap(){{
                put("utterance", CollectionUtils.fetch(outputData, new Fetcher<RobotData, String>() {
                    @Override
                    public String fetch(RobotData robotData) {
                        return robotData.getReturnVal();
                    }
                }));
                put("sessionId", robot.getSessionId());
            }});
        }catch (TimeOutException e) {
//            logger.error("error : {}", e.getMessage());
            return ResultData.error(ResultCode.ERROR);
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 聊天对话
     * @param robotCode
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "/heartbeat/{robotCode}/{sessionId}.json")
    @ResponseBody
    public ResultData pullMessage(@PathVariable("robotCode") String robotCode,
                           @PathVariable("sessionId") String sessionId){
        logger.debug("request : {},{}", robotCode, sessionId);
        try{
            final Robot robot = RobotManager.getRobot(robotCode, sessionId);
            SyncRobotUserInterface ui = (SyncRobotUserInterface)robot.getUserInterface();
            final List<RobotData> outputData = ui.pollAllOutputData();
            return ResultData.success(new HashedMap(){{
                put("utterance", CollectionUtils.fetch(outputData, new Fetcher<RobotData, String>() {
                    @Override
                    public String fetch(RobotData robotData) {
                        return robotData.getReturnVal();
                    }
                }));
                put("sessionId", robot.getSessionId());
            }});
        }catch (TimeOutException e) {
//            logger.error("error : {}", e.getMessage());
            return ResultData.error(ResultCode.ERROR);
        }catch (Exception e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
     * 关闭聊天会话
     * @param robotCode
     * @return
     */
    @RequestMapping(value = "/disc/{robotCode}/{sessionId}.json")
    @ResponseBody
    public ResultData disconnection(@PathVariable("robotCode") String robotCode,
                                    @PathVariable("sessionId") String sessionId){
        try {
            RobotManager.removeRobot(robotCode, sessionId);
        } catch (IOException e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.success(Maps.newHashMap());
    }

    /**
     * 保存聊天会话
     * @param robotCode
     * @return
     */
    @RequestMapping(value = "/save/{robotCode}/{sessionId}.json")
    @ResponseBody
    public ResultData save(@PathVariable("robotCode") String robotCode,
                                    @PathVariable("sessionId") String sessionId){
        try {
            RobotManager.saveRobot(robotCode, sessionId);
        } catch (IOException e) {
            logger.error("error : {}", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.success(Maps.newHashMap());
    }

}
