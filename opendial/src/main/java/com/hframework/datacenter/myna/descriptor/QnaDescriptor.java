package com.hframework.datacenter.myna.descriptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hframework.datacenter.myna.BusinessHandler;
import com.hframework.datacenter.myna.ConfigurationXmlBuilder;
import com.hframework.myna.config.domain.model.SemanticParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QnaDescriptor{

    private String code;//问答编码
    private String next;//该问题问答结束后下一个节点编码

    private String curPostHandleCode; //当前问答结束后扩展
    private String nextPreHandleCode;//该问题问答结束后下一个节点编码

    private String[] groundAndConfirmRate = new String[2];
    private int waitTimeOut = -1;
    private List<String> waitTimeOutUtterances; //超时话术

    private String groundUtil; //落地效用值
    private String confirmUtil; //确认效用值
    private String askRepeatUtil;//重问效用值

    private int breakQAThreshold;//退出问答阈值
    private int breakSessionThreshold;//退出会话阈值


    private String prevExtendProb; //前值继承概率
    private String prevConfirmExtendProb; //确认问答前值继承概率
    private String prevDisConfirmExtendProb; //否认问答前值继承概率

    private boolean isJudgmentQna;//是否为流程判断节点

    private String utterance; //询问话术
    private String groundUtterance; //落地话术
    private String confirmUtterance; //确认话术
    private List<String> askRepeatUtterances; //重问话术
    private List<JudgmentBranchDescriptor> branchs; //判断节点分支事件描述
    @JsonIgnore
    private List<OptionPriorProbDescriptor> priorProbList;

    private List<SemanticParser> semanticParsers;
    @JsonIgnore
    private Map<String, Integer> options;
    @JsonIgnore
    private Map<String, String> optionAliases;

    private BusinessHandler preHandler;
    private BusinessHandler postHandler;
    private String preHandleCode; //前置处理
    private String postHandleCode;//后置处理

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroundUtil() {
        return groundUtil;
    }

    public void setGroundUtil(String groundUtil) {
        this.groundUtil = groundUtil;
    }

    public String getConfirmUtil() {
        return confirmUtil;
    }

    public void setConfirmUtil(String confirmUtil) {
        this.confirmUtil = confirmUtil;
    }

    public String getAskRepeatUtil() {
        return askRepeatUtil;
    }

    public void setAskRepeatUtil(String askRepeatUtil) {
        this.askRepeatUtil = askRepeatUtil;
    }

    public int getBreakQAThreshold() {
        return breakQAThreshold;
    }

    public void setBreakQAThreshold(int breakQAThreshold) {
        this.breakQAThreshold = breakQAThreshold;
    }

    public int getBreakSessionThreshold() {
        return breakSessionThreshold;
    }

    public void setBreakSessionThreshold(int breakSessionThreshold) {
        this.breakSessionThreshold = breakSessionThreshold;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getUtterance() {
        return utterance;
    }

    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }

    public String getGroundUtterance() {
        return groundUtterance;
    }

    public void setGroundUtterance(String groundUtterance) {
        this.groundUtterance = groundUtterance;
    }

    public String getConfirmUtterance() {
        return confirmUtterance;
    }

    public void setConfirmUtterance(String confirmUtterance) {
        this.confirmUtterance = confirmUtterance;
    }

    public List<String> getAskRepeatUtterances() {
        return askRepeatUtterances;
    }

    public void setAskRepeatUtterances(List<String> askRepeatUtterances) {
        this.askRepeatUtterances = askRepeatUtterances;
    }

    public List<JudgmentBranchDescriptor> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<JudgmentBranchDescriptor> branchs) {
        this.branchs = branchs;
    }

    public void addBranch(JudgmentBranchDescriptor branch) {
        if(branchs == null) {
            branchs = new ArrayList<>();
        }
        branchs.add(branch);
    }

    public List<OptionPriorProbDescriptor> getPriorProbList() {
        return priorProbList;
    }

    public void setPriorProbList(List<OptionPriorProbDescriptor> priorProbList) {
        this.priorProbList = priorProbList;
    }
    public void addPriorProb(OptionPriorProbDescriptor priorProb) {
        if(priorProbList == null) priorProbList = new ArrayList<>();
        priorProbList.add(priorProb);
    }

    public boolean isJudgmentQna() {
        return isJudgmentQna;
    }

    public void setJudgmentQna(boolean judgmentQna) {
        isJudgmentQna = judgmentQna;
    }

    public String getPrevExtendProb() {
        return prevExtendProb;
    }

    public void setPrevExtendProb(String prevExtendProb) {
        this.prevExtendProb = prevExtendProb;
    }

    public String getPrevConfirmExtendProb() {
        return prevConfirmExtendProb;
    }

    public void setPrevConfirmExtendProb(String prevConfirmExtendProb) {
        this.prevConfirmExtendProb = prevConfirmExtendProb;
    }

    public String getPrevDisConfirmExtendProb() {
        return prevDisConfirmExtendProb;
    }

    public void setPrevDisConfirmExtendProb(String prevDisConfirmExtendProb) {
        this.prevDisConfirmExtendProb = prevDisConfirmExtendProb;
    }

    public List<SemanticParser> getSemanticParsers() {
        return semanticParsers;
    }

    public void setSemanticParsers(List<SemanticParser> semanticParsers) {
        this.semanticParsers = semanticParsers;
    }

    public void setOptions(Map<String, Integer>  options) {
        this.options = options;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }

    public BusinessHandler getPreHandler() {
        return preHandler;
    }

    public void setPreHandler(BusinessHandler preHandler) {
        this.preHandler = preHandler;
    }

    public BusinessHandler getPostHandler() {
        return postHandler;
    }

    public void setPostHandler(BusinessHandler postHandler) {
        this.postHandler = postHandler;
    }

    public String getCurPostHandleCode() {
        return curPostHandleCode;
    }

    public void setCurPostHandleCode(String curPostHandleCode) {
        this.curPostHandleCode = curPostHandleCode;
    }

    public String getNextPreHandleCode() {
        return nextPreHandleCode;
    }

    public void setNextPreHandleCode(String nextPreHandleCode) {
        this.nextPreHandleCode = nextPreHandleCode;
    }

    public String getPreHandleCode() {
        return preHandleCode;
    }

    public void setPreHandleCode(String preHandleCode) {
        this.preHandleCode = preHandleCode;
    }

    public String getPostHandleCode() {
        return postHandleCode;
    }

    public void setPostHandleCode(String postHandleCode) {
        this.postHandleCode = postHandleCode;
    }

    public String[] getGroundAndConfirmRate() {
        return groundAndConfirmRate;
    }

    public void setGroundAndConfirmRate(String[] groundAndConfirmRate) {
        this.groundAndConfirmRate = groundAndConfirmRate;
    }

    public int getWaitTimeOut() {
        return waitTimeOut;
    }

    public void setWaitTimeOut(int waitTimeOut) {
        this.waitTimeOut = waitTimeOut;
    }

    public List<String> getWaitTimeOutUtterances() {
        return waitTimeOutUtterances;
    }

    public void setWaitTimeOutUtterances(List<String> waitTimeOutUtterances) {
        this.waitTimeOutUtterances = waitTimeOutUtterances;
    }

    public Map<String, String> getOptionAliases() {
//        return optionAliases;
        //这里改为实时计算获取，原因是当我们添加词库时，由于词库与任务并没有关系，导致任务的qna等信息并不会实时刷新，这里通过损失性能来获得时效性
        Map<String, String> optionAliases = ConfigurationXmlBuilder.getQnaOptionAliases(getOptions());
        return optionAliases;
    }

    public void setOptionAliases(Map<String, String> optionAliases) {
        this.optionAliases = optionAliases;
    }

    public boolean isEndingQA(){
        return "Final".equals(this.getNext()) && this.getOptions().size() <= 1;
    }
}