package com.hframework.datacenter.myna.descriptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hframework.common.monitor.Node;
import com.hframework.datacenter.myna.CorpusDescriptor;
import com.hframework.myna.config.domain.model.Corpus;
import com.hframework.myna.config.domain.model.Qna;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationDescriptor {

    private String taskCode; //任务编码
    private String initCurrentStep;//初始化阶段
    private String welcomeUtterance; //欢迎话术
    private String finalUtterance; //任务完成话术
    private String closeUtterance; //再见话术

    @Deprecated
    private String prevExtendProb; //前值继承概率,该值应该随不同的QA而变化
    @Deprecated
    private String prevConfirmExtendProb; //确认问答前值继承概率,该值应该随不同的QA而变化
    @Deprecated
    private String prevDisconfirmExtendProb; //否认问答前值继承概率,该值应该随不同的QA而变化

    private String pubGroundUtil; //落地效用值
    private String pubConfirmUtil; //确认效用值
    private String pubAskRepeatUtil; //重问效用值

    private List<QnaDescriptor> qnas = new ArrayList<>();//所有流程节点问答
    @JsonIgnore
    private List<QnaDescriptor> judgmentQnas = new ArrayList<>();//流程判断节点问答
    @JsonIgnore
    private List<QnaDescriptor> slotQnas = new ArrayList<>();//流程取值节点问答

    private List<CorpusDescriptor> corpusList;//语义解析辅助识别预料

    private short utteranceSelectMethod;

    public String getInitCurrentStep() {
        return initCurrentStep;
    }

    public void setInitCurrentStep(String initCurrentStep) {
        this.initCurrentStep = initCurrentStep;
    }

    public String getWelcomeUtterance() {
        return welcomeUtterance;
    }

    public void setWelcomeUtterance(String welcomeUtterance) {
        this.welcomeUtterance = welcomeUtterance;
    }

    public String getFinalUtterance() {
        return finalUtterance;
    }

    public void setFinalUtterance(String finalUtterance) {
        this.finalUtterance = finalUtterance;
    }

    public String getCloseUtterance() {
        return closeUtterance;
    }

    public void setCloseUtterance(String closeUtterance) {
        this.closeUtterance = closeUtterance;
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

    public String getPrevDisconfirmExtendProb() {
        return prevDisconfirmExtendProb;
    }

    public void setPrevDisconfirmExtendProb(String prevDisconfirmExtendProb) {
        this.prevDisconfirmExtendProb = prevDisconfirmExtendProb;
    }

    public String getPubGroundUtil() {
        return pubGroundUtil;
    }

    public void setPubGroundUtil(String pubGroundUtil) {
        this.pubGroundUtil = pubGroundUtil;
    }

    public String getPubConfirmUtil() {
        return pubConfirmUtil;
    }

    public void setPubConfirmUtil(String pubConfirmUtil) {
        this.pubConfirmUtil = pubConfirmUtil;
    }

    public String getPubAskRepeatUtil() {
        return pubAskRepeatUtil;
    }

    public void setPubAskRepeatUtil(String pubAskRepeatUtil) {
        this.pubAskRepeatUtil = pubAskRepeatUtil;
    }

    public List<QnaDescriptor> getQnas() {
        return qnas;
    }

    public void addQna(QnaDescriptor qna) {
        qnas.add(qna);
        if(qna.isJudgmentQna()) {
            judgmentQnas.add(qna);
        }else {
            slotQnas.add(qna);
        }
    }

    public List<QnaDescriptor> getJudgmentQnas() {
        return judgmentQnas;
    }

    public List<QnaDescriptor> getSlotQnas() {
        return slotQnas;
    }

    public short getUtteranceSelectMethod() {
        return utteranceSelectMethod;
    }

    public void setUtteranceSelectMethod(short utteranceSelectMethod) {
        this.utteranceSelectMethod = utteranceSelectMethod;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public List<CorpusDescriptor> getCorpusList() {
        return corpusList;
    }

    public void setCorpusList(List<CorpusDescriptor> corpusList) {
        this.corpusList = corpusList;
    }
}

