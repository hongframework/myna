package com.hframework.datacenter.myna;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.hframework.common.monitor.Node;
import com.hframework.common.util.DateUtils;
import com.hframework.common.util.RegexUtils;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.collect.CollectionUtils;
import com.hframework.common.util.collect.bean.Fetcher;
import com.hframework.common.util.message.JsonUtils;
import com.hframework.common.util.message.VelocityUtil;
import com.hframework.datacenter.myna.descriptor.ConfigurationDescriptor;
import com.hframework.datacenter.myna.descriptor.JudgmentBranchDescriptor;
import com.hframework.datacenter.myna.descriptor.OptionPriorProbDescriptor;
import com.hframework.datacenter.myna.descriptor.QnaDescriptor;
import com.hframework.myna.config.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ConfigurationXmlBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationXmlBuilder.class);

    public static String[] buildXml(ConfigurationDescriptor descriptor) {
        logger.info("configuration to xml start..");
        try {
            System.out.println(JsonUtils.writeValueAsString(descriptor));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        map.put("descriptor", descriptor);
        String content = VelocityUtil.produceTemplateContent("vm/opendial/main.vm", map);
        String priorProp = VelocityUtil.produceTemplateContent("vm/opendial/priorprob.vm", map);
        return new String[]{content, priorProp};
    }

    public static String transference(String str){
        str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        if(str.contains("*")) {
            String[] multiplies = RegexUtils.find(str, "\\{[^}]*\\*[^}]*\\}");
            for (String multiply : multiplies) {
                str = str.replace(multiply, multiply.replaceAll("\\*","×"));
            }
        }
        return str;
    }

    public static JSONObject buildFAQJson(Node<Task> taskNode) {
        Task task = taskNode.getObject();
        List<Node<P2pReply>> p2pReplayNodes = taskNode.getOutputs(P2pReply.class);
        JSONArray array = new JSONArray();
        Byte matchingType = 1;
        for (Node<P2pReply> p2pReplayNode : p2pReplayNodes) {
            P2pReply p2pReply = p2pReplayNode.getObject();
            JSONObject object = new JSONObject();
            object.put("summary", p2pReply.getTitle());
            object.put("answer", p2pReply.getAnswer());
            Set<String> questions = new HashSet<>();

            List<Node<P2pCorpus>> p2pCorpusNodes = p2pReplayNode.getOutputs(P2pCorpus.class);
            for (Node<P2pCorpus> p2pCorpusNode : p2pCorpusNodes) {
                matchingType = p2pCorpusNode.getObject().getMatchingType();
                String corpuss = p2pCorpusNode.getObject().getCorpuss();
                for (String optionItem : corpuss.split("[,，、/]+")) {
                    if(StringUtils.isNotBlank(optionItem)) {
                        questions.add(optionItem);
                    }
                }
            }
            object.put("question", questions);
            object.put("matching", matchingType == 0 ? "complete" : (matchingType == 0 ? "absolute" : "similarity"));
            array.add(object);
        }
        JSONObject info = new JSONObject();
        info.put("matching", matchingType == 0 ? "complete" : (matchingType == 0 ? "absolute" : "similarity"));
        info.put("code", task.getCode());
        info.put("name", task.getName());
        info.put("genetime", DateUtils.getCurrentDateYYYYMMDDHHMMSS());

        JSONObject result = new JSONObject();
        result.put("info", info);
        result.put("qnas", array);
        return result;
    }

    public static ConfigurationDescriptor buildDescriptor(Node taskNode) {
        ConfigurationDescriptor descriptor = new ConfigurationDescriptor();
        List<Node<Qna>> qnaList = taskNode.getOutputs(Qna.class, new Comparator<Qna>(){

            @Override
            public int compare(Qna o1, Qna o2) {
                return (int) (o1.getId() - o2.getId());
            }
        });
        List<Node<Corpus>> corpusList = taskNode.getOutputs(Corpus.class);
        Node<Model> model = taskNode.getInput(Model.class);
        Task task = (Task)taskNode.getObject();
        descriptor.setTaskCode(task.getCode());
        descriptor.setWelcomeUtterance(task.getWelcomeUtterance());
        descriptor.setFinalUtterance(task.getFinalUtterance());
        descriptor.setCloseUtterance(task.getCloseUtterance());
        descriptor.setInitCurrentStep(qnaList.get(0).getObject().getKeyword());
        descriptor.setCorpusList(CollectionUtils.fetch(corpusList, new Fetcher<Node<Corpus>, CorpusDescriptor>() {
            @Override
            public CorpusDescriptor fetch(Node<Corpus> corpusNode) {
                return new CorpusDescriptor(corpusNode.getObject());
            }
        }));

        descriptor.setPrevExtendProb("0.3");
        descriptor.setPrevConfirmExtendProb("0.2");
        descriptor.setPrevDisconfirmExtendProb("0.2");

        descriptor.setPubGroundUtil("-4.5");
        descriptor.setPubConfirmUtil("-0.3");
        descriptor.setPubAskRepeatUtil("-0.03");

        descriptor.setUtteranceSelectMethod(model.getObject() != null ? model.getObject().getUtteranceSelectMethod(): (short)1);

//        Map<String, Object> modelMap = new HashMap<>();
//        modelMap.put("confirmUtil","0.5");
//        modelMap.put("askRepeatUtil","0.1");

        Map<String, Map<String, Object>> actionsMap = new HashMap<>();
        if(model != null) {
            List<Node<ModelActions>> modelActionsList = model.getOutputs(ModelActions.class);
            for (Node<ModelActions> modelActions : modelActionsList) {
                Node<Action> actionNode = modelActions.getInput(Action.class);
                List<Node<Utterance>> utteranceNodes = modelActions.getInputs(Utterance.class);

                if(utteranceNodes.isEmpty()) {
                    Node<Utterance> utterance = actionNode.getInput(Utterance.class);
                    if(utterance != null) {
                        utteranceNodes.add(utterance);
                    }
                }

                Action action = actionNode.getObject();
                String actionCode = action.getCode();
                final String actionName = action.getName();
                final String triggerValue = StringUtils.isNotBlank(modelActions.getObject().getActionTriggerValue()) ?
                        modelActions.getObject().getActionTriggerValue() : action.getTriggerDefaultValue();
                final List<String> utterances = CollectionUtils.fetch(utteranceNodes, new Fetcher<Node<Utterance>, String>() {
                    @Override
                    public String fetch(Node<Utterance> utteranceNode) {
                        return transference(utteranceNode.getObject().getContent());
                    }
                });
                actionsMap.put(actionCode, new HashMap(){{
                    put("name", actionName);
                    put("value", triggerValue);
                    put("utterance", utterances);
                }});
            }
        }

        actionsMap = getTaskActionsMap(actionsMap, taskNode);

        Map<QnaDescriptor, Set<String>> qnaForwardToCacheMap = new HashMap<>();
        QnaDescriptor preQnaDescriptor = null;
        for (int i = 0; i < qnaList.size(); i++) {
            Node<Qna> qnaNode = qnaList.get(i);
            Qna qnaObject = qnaNode.getObject();
            Qna nextObject = qnaList.size() > i + 1 ? qnaList.get(i + 1).getObject() : null;

            QnaDescriptor qnaDescriptor= new QnaDescriptor();;
            qnaDescriptor.setCode(qnaObject.getKeyword());
            qnaDescriptor.setNext(nextObject != null? nextObject.getKeyword() : "Final");

            qnaDescriptor.setUtterance(transference(qnaObject.getDescription()));

//            qnaDescriptor.setGroundUtil(String.valueOf(5));
            qnaDescriptor.setAskRepeatUtil(String.valueOf(0.1));//这样askRepeat的util恒等于0.1-0.03 = 0.07,以askRepeat为基线可进行计算



            Map<String, Map<String, Object>> qnaActionsMap = getQnaActionsMap(actionsMap, qnaNode);

            setConfirmAndGroundUtil(qnaDescriptor, qnaActionsMap, descriptor);

            qnaForwardToCacheMap.put(qnaDescriptor, qnaDescriptor.getNext() != null ?
                    Sets.newHashSet(qnaDescriptor.getNext()) : Sets.<String>newHashSet());

            String falseForwardCode = addBranchCaseBooleanQnA(qnaDescriptor, qnaObject, qnaActionsMap);
            if(StringUtils.isNotBlank(falseForwardCode)) {
                qnaForwardToCacheMap.get(qnaDescriptor).add(falseForwardCode);
            }

            descriptor.addQna(qnaDescriptor);

            for (Map.Entry<String, Map<String, Object>> entry : qnaActionsMap.entrySet()) {
                String actionCode = entry.getKey();
                String value = (String) entry.getValue().get("value");
                List<String> utterances = (List<String>) entry.getValue().get("utterance");
                if("GotoNext".equals(actionCode)) { //下一各节点
                    qnaDescriptor.setNext(value);
                }else if("AskRepeat".equals(actionCode)) { //重问
                    qnaDescriptor.setAskRepeatUtterances(utterances);
                }else if("Confirm".equals(actionCode)) { //确认
                    qnaDescriptor.setConfirmUtterance(utterances.get(0));
                }else if("BreakQA".equals(actionCode)) { //问答中止
                    qnaDescriptor.setBreakQAThreshold(Integer.valueOf(value));
                }else if("BreakTask".equals(actionCode)) { //会话中止
                    qnaDescriptor.setBreakSessionThreshold(Integer.valueOf(value));
                }else if("GroundQA".equals(actionCode)) { //落地会话
                    qnaDescriptor.setGroundUtterance(utterances.get(0));
                    if(qnaDescriptor.getBranchs() != null && qnaDescriptor.getBranchs().size() > 0) {
                        qnaDescriptor.getBranchs().get(0).setGroundUtterance(utterances.get(0));
                    }
                }else if("LastConfirm".equals(actionCode)) { //最终确认 TODO
                }else if("WaitTimeOut".equals(actionCode)) { //等待超时
                    qnaDescriptor.setWaitTimeOut(Integer.valueOf(value));
                    qnaDescriptor.setWaitTimeOutUtterances(utterances);
                }else if("PreHandle".equals(actionCode)) { //前置处理
                    qnaDescriptor.setPreHandleCode(value);
                    qnaDescriptor.setPreHandler(RobotManager.getBusinessHandler(value));
                    setRelatQnaOrBranchNextPreHandle(qnaForwardToCacheMap, qnaDescriptor.getCode(), value);
                }else if("PostHandle".equals(actionCode)) { //后置处理
                    qnaDescriptor.setPostHandleCode(value);
                    qnaDescriptor.setPostHandler(RobotManager.getBusinessHandler(value));
                    qnaDescriptor.setCurPostHandleCode(value);
                    setTrueBranchCurPostHandleIfExists(qnaDescriptor, value);
                }else if(actionCode.startsWith("PrioriProb")) { //前值参考率
                    if(qnaDescriptor.isJudgmentQna()) {
                        qnaDescriptor.setPrevConfirmExtendProb(value);
                        qnaDescriptor.setPrevDisConfirmExtendProb(value);
                    }else {
                        qnaDescriptor.setPrevExtendProb(value);
                    }
                }else if(!"ConfirmDisagree".equals(actionCode)){//不是[不同意确认信息]
                    throw new RuntimeException(actionCode + " not support!");
                }
            }
            Map<String, Integer> qnaOptionsMap = getQnaOptionsSet(qnaNode);
            qnaDescriptor.setOptions(qnaOptionsMap);

            Map<String, String> optionAliases = getQnaOptionAliases(qnaOptionsMap);
            qnaDescriptor.setOptionAliases(optionAliases);

            Map<String, String> options = getQnaOptionsMap(qnaNode);
            for (Map.Entry<String, String> entry : options.entrySet()) {
                qnaDescriptor.addPriorProb(new OptionPriorProbDescriptor(entry.getKey(), entry.getValue()));
            }
            qnaDescriptor.setSemanticParsers(getQnaSemanticParsers(qnaNode));

            qnaNode.getObject().getSemanticParsers();

            preQnaDescriptor = qnaDescriptor;
        }
        return descriptor;
    }

    public static Map<String, String> getQnaOptionAliases(Map<String, Integer> qnaOptionsMap) {
        Map<String, String> result = new HashMap<>();
        Set<String> strings = qnaOptionsMap.keySet();
        Collection<Node> thesaurusNodes = RobotManager.getNodeNetwork().get(Thesaurus.class).values();
        for (Node node : thesaurusNodes) {
            Thesaurus thesaurus = (Thesaurus) node.getObject();
            if(strings.contains(thesaurus.getName().trim())) {
                List<Node<Words>> outputNodes = node.getOutputs(Words.class);
                for (Node<Words> outputNode : outputNodes) {
                    for (String optionItem : outputNode.getObject().getName().split("[,，、/]+")) {
                        if(com.hframework.common.util.StringUtils.isNotBlank(optionItem)) {
                            result.put(optionItem, thesaurus.getName().trim());
                        }
                    }
                }
            }
        }
        return result;
    }

    private static Map<String, Map<String, Object>> getTaskActionsMap(Map<String, Map<String, Object>> actionsMap, Node taskNode) {
        Map<String, Map<String, Object>> newActionMap =new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : actionsMap.entrySet()) {
            newActionMap.put(entry.getKey(), new HashMap<String, Object>(entry.getValue()));
        }
        List<Node<TaskActions>> taskActionsList = taskNode.getOutputs(TaskActions.class);
        for (Node<TaskActions> taskActionsNode : taskActionsList) {
            overwriteActionMap(taskActionsNode, newActionMap, taskActionsNode.getObject().getActionTriggerValue());
        }
        return newActionMap;
    }

    private static <T> void overwriteActionMap(Node<T> taskActionsNode, Map<String, Map<String, Object>> newActionMap, String actionTriggerValue) {

        Node<Action> actionNode = taskActionsNode.getInput(Action.class);
        final Action action = actionNode.getObject();
        String actionCode = action.getCode();

        if(!newActionMap.containsKey(actionCode)) {
            final Node<Utterance> utterance = actionNode.getInput(Utterance.class);
            newActionMap.put(actionCode, new HashMap(){{
                put("name", action.getName());
                put("value", action.getTriggerDefaultValue());
                put("utterance", utterance == null ? null : utterance.getObject().getContent());
            }});
        }
        Map<String, Object> actionSettingMap = newActionMap.get(actionCode);
        List<Node<Utterance>> utteranceNodes = taskActionsNode.getInputs(Utterance.class);
        if(!utteranceNodes.isEmpty()) {
            actionSettingMap.put("utterance", CollectionUtils.fetch(utteranceNodes, new Fetcher<Node<Utterance>, String>() {
                @Override
                public String fetch(Node<Utterance> utteranceNode) {
                    return utteranceNode.getObject().getContent();
                }
            }));
        }
        if(StringUtils.isNotBlank(actionTriggerValue)) {
            actionSettingMap.put("value", actionTriggerValue);
        }
    }


    private static void setRelatQnaOrBranchNextPreHandle(Map<QnaDescriptor, Set<String>> falseForwardCodeCacheMap, String code, String value) {
        for (Map.Entry<QnaDescriptor, Set<String>> entry : falseForwardCodeCacheMap.entrySet()) {
            if(entry.getValue().contains(code)) {
                QnaDescriptor relQnaDescriptor = entry.getKey();
                if(code.equals(relQnaDescriptor.getNext())) {
                    relQnaDescriptor.setNextPreHandleCode(value);
                }
                if(entry.getKey().getBranchs() != null) {
                    for (JudgmentBranchDescriptor judgmentBranchDescriptor : entry.getKey().getBranchs()) {
                        if(code.equals(judgmentBranchDescriptor.getNext())) {
                            judgmentBranchDescriptor.setNextPreHandleCode(value);
                        }
                    }
                }
            }
        }
    }

    private static void setTrueBranchCurPostHandleIfExists(QnaDescriptor qnaDescriptor, String handleCode) {
        if(qnaDescriptor.getBranchs() != null && qnaDescriptor.getBranchs().size() > 0) {
            qnaDescriptor.getBranchs().get(0).setCurPostHandleCode(handleCode);
        }
    }

    private static String addBranchCaseBooleanQnA(QnaDescriptor qnaDescriptor, Qna qnaObject,
                                                Map<String, Map<String, Object>> qnaActionsMap) {
        if((byte)1 == qnaObject.getAnswerType()) {
            qnaDescriptor.setJudgmentQna(true);
            qnaDescriptor.addBranch(new JudgmentBranchDescriptor("true", qnaDescriptor.getNext(), qnaDescriptor.getUtterance()));
        }
        String value = null;
        if(qnaActionsMap.containsKey("ConfirmDisagree")) {//不同意确认信息
            Map<String, Object> askRepeat = qnaActionsMap.get("ConfirmDisagree");
            value = (String) askRepeat.get("value");
            qnaDescriptor.addBranch(new JudgmentBranchDescriptor("false", value, null));
        }
        return value;

    }

    private static void setConfirmAndGroundUtil(QnaDescriptor qnaDescriptor, Map<String, Map<String, Object>>
            qnaActionsMap, ConfigurationDescriptor descriptor) {
        if(qnaActionsMap.containsKey("AskRepeat")) {
            Map<String, Object> askRepeat = qnaActionsMap.get("AskRepeat");
            String value = (String) askRepeat.get("value");
            qnaDescriptor.getGroundAndConfirmRate()[1] = value;
            qnaDescriptor.setConfirmUtil(calcConfirmUtil(qnaDescriptor.getAskRepeatUtil(),
                    descriptor.getPubAskRepeatUtil(), descriptor.getPubConfirmUtil(), value));
        }
        if(qnaActionsMap.containsKey("Confirm")) {
            Map<String, Object> askRepeat = qnaActionsMap.get("Confirm");
            String value = (String) askRepeat.get("value");
            qnaDescriptor.getGroundAndConfirmRate()[0] = value;
            qnaDescriptor.setGroundUtil(calcGroundUtil(qnaDescriptor.getAskRepeatUtil(),
                    descriptor.getPubAskRepeatUtil(), qnaDescriptor.getConfirmUtil(),
                    descriptor.getPubConfirmUtil(), descriptor.getPubGroundUtil(), value));
        }
    }


    private static List<SemanticParser> getQnaSemanticParsers(Node<Qna> qnaNode) {
        if(StringUtils.isNotBlank(qnaNode.getObject().getSemanticParsers())) {
            return CollectionUtils.fetch(qnaNode.getInputs(SemanticParser.class), new Fetcher<Node<SemanticParser>, SemanticParser>() {
                @Override
                public SemanticParser fetch(Node<SemanticParser> semanticParserNode) {
                    return semanticParserNode.getObject();
                }
            });
        }else {
            Byte answerType = qnaNode.getObject().getAnswerType();
            List<SemanticParser> result = new ArrayList<>();
            if(answerType != null) {
                Map<String, Node> nodeMap = RobotManager.getNodeNetwork().get(SemanticParser.class);
                for (Node node : nodeMap.values()) {
                    if(answerType.equals(((SemanticParser)node.getObject()).getParseType()) && ((SemanticParser)node.getObject()).getIsDefault() == (byte) 1) {
                        result.add((SemanticParser) node.getObject());
                    }
                }
            }
            return result;
        }
    }

    private static Map<String, Integer> getQnaOptionsSet(Node<Qna> qnaNode) {
        Node<Options> options = qnaNode.getInput(Options.class);
        Map<String, Integer> tempMap = new HashMap<>();
        if(options != null) {
            int i = 0;
            for (Node<Option> option : options.getOutputs(Option.class)) {

                for (String optionItem : option.getObject().getName().split("[,，、/]+")) {
                    if(StringUtils.isNotBlank(optionItem)) {
                        tempMap.put(optionItem, i );
                    }
                }
                i++ ;
            }
        }
        return tempMap;
    }

    private static Map<String, String> getQnaOptionsMap(Node<Qna> qnaNode) {
        Map<String, String> result = new LinkedHashMap<>();
        Node<Options> options = qnaNode.getInput(Options.class);

        Map<Long, Set<String>> optionTempMap = new HashMap<>();
        Set<String> tempSet = new HashSet<>();
        if(options != null) {
            for (Node<Option> option : options.getOutputs(Option.class)) {
                for (String optionItem : option.getObject().getName().split("[,，、/]+")) {
                    if(!optionTempMap.containsKey(option.getObject().getUtility())) {
                        optionTempMap.put(option.getObject().getUtility(), new HashSet<String>());
                    }
                    if(StringUtils.isNotBlank(optionItem) && !tempSet.contains(optionItem)) {
                        optionTempMap.get(option.getObject().getUtility()).add(optionItem);
                        tempSet.add(optionItem);
                    }
                }
            }
            if(optionTempMap.size() <= 1) return result;

            long totalProb = 0;
            for (Map.Entry<Long, Set<String>> longSetEntry : optionTempMap.entrySet()) {
                totalProb += (longSetEntry.getKey() * longSetEntry.getValue().size());
            }
            for (Map.Entry<Long, Set<String>> longSetEntry : optionTempMap.entrySet()) {
                String prob =  new BigDecimal(longSetEntry.getKey()).divide(new BigDecimal(totalProb),4, RoundingMode.FLOOR).toString();
                for (String item : longSetEntry.getValue()) {
                    result.put(item, prob);
                }
            }
        }
        return result;
    }

    private static String calcUtilByRatio(String pubGroundUtil, String pubConfirmUtil, String groundUtil, String thresholdRatio) {
        if(new BigDecimal(thresholdRatio).compareTo(BigDecimal.ZERO) <= 0) {
            new RuntimeException("threshold ratio must great than zero !");
        }
        return new BigDecimal(groundUtil).add(
                new BigDecimal(pubGroundUtil).subtract(new BigDecimal(pubConfirmUtil))
                        .divide(new BigDecimal(thresholdRatio).divide(BigDecimal.valueOf(10)), 3, RoundingMode.HALF_DOWN)
        ).toPlainString();
    }

    private static String calcGroundUtil(String askRepeatUtil, String pubAskRepeatUtil,
                                         String confirmUtil, String pubConfirmUtil, String pubGroundUtil, String thresholdRatio) {



        BigDecimal confirmThreshold = new BigDecimal(thresholdRatio).multiply(BigDecimal.valueOf(0.1))
                .multiply(new BigDecimal(confirmUtil)).subtract(new BigDecimal(pubConfirmUtil));
        BigDecimal askRepeatThreshold = new BigDecimal(askRepeatUtil).subtract(new BigDecimal(pubAskRepeatUtil));
        return (askRepeatThreshold.compareTo(confirmThreshold) > 0 ? askRepeatThreshold : confirmThreshold)
                .subtract(new BigDecimal(pubGroundUtil))
                .divide(new BigDecimal(thresholdRatio).multiply(BigDecimal.valueOf(0.1)), 3, RoundingMode.HALF_DOWN).toPlainString();
    }

    /**
     * 根据需求比例计算确认的效用值
     * @param askRepeatUtil
     * @param pubAskRepeatUtil
     * @param pubConfirmUtil
     * @param thresholdRatio
     * @return
     */
    private static String calcConfirmUtil(String askRepeatUtil, String pubAskRepeatUtil, String pubConfirmUtil, String thresholdRatio) {
        if(new BigDecimal(thresholdRatio).compareTo(BigDecimal.ZERO) <= 0) {
            new RuntimeException("threshold ratio must great than zero !");
        }
        BigDecimal askRepeatThreshold = new BigDecimal(askRepeatUtil).subtract(new BigDecimal(pubAskRepeatUtil));
        return askRepeatThreshold
                .subtract(new BigDecimal(pubConfirmUtil))
                .divide(new BigDecimal(thresholdRatio).multiply(BigDecimal.valueOf(0.1)), 3, RoundingMode.HALF_DOWN).toPlainString();
    }

    private static Map<String, Map<String, Object>> getQnaActionsMap(Map<String, Map<String, Object>> actionsMap, Node<Qna> qnaNode) {
        Map<String, Map<String, Object>> qnaActionMap =new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : actionsMap.entrySet()) {
            qnaActionMap.put(entry.getKey(), new HashMap<String, Object>(entry.getValue()));
        }
        List<Node<QnaActions>> qnaActionsList = qnaNode.getOutputs(QnaActions.class);
        for (Node<QnaActions> qnaActionsNode : qnaActionsList) {
            overwriteActionMap(qnaActionsNode, qnaActionMap, qnaActionsNode.getObject().getActionTriggerValue());
        }
        return qnaActionMap;
    }



}
