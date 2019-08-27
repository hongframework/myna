package com.hframework.myna.config.domain.model;

import java.util.Date;

public class QnaActions {
    private Long id;

    private Long answerId;

    private Long actionId;

    private String actionTriggerValue;

    private String utterances;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public QnaActions(Long id, Long answerId, Long actionId, String actionTriggerValue, String utterances, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.answerId = answerId;
        this.actionId = actionId;
        this.actionTriggerValue = actionTriggerValue;
        this.utterances = utterances;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public Long getActionId() {
        return actionId;
    }

    public String getActionTriggerValue() {
        return actionTriggerValue;
    }

    public String getUtterances() {
        return utterances;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setAnswerId(Long answerId) {
        this.answerId=answerId;
    }

    public void setActionId(Long actionId) {
        this.actionId=actionId;
    }

    public void setActionTriggerValue(String actionTriggerValue) {
        this.actionTriggerValue=actionTriggerValue;
    }

    public void setUtterances(String utterances) {
        this.utterances=utterances;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId=creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId=modifierId;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime=modifyTime;
    }

    public QnaActions() {
        super();
    }
}