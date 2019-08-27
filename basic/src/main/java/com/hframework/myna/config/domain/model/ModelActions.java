package com.hframework.myna.config.domain.model;

import java.util.Date;

public class ModelActions {
    private Long id;

    private Long modelId;

    private Long actionId;

    private String actionTriggerValue;

    private String utterances;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public ModelActions(Long id, Long modelId, Long actionId, String actionTriggerValue, String utterances, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.modelId = modelId;
        this.actionId = actionId;
        this.actionTriggerValue = actionTriggerValue;
        this.utterances = utterances;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Long getModelId() {
        return modelId;
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

    public Byte getStatus() {
        return status;
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

    public void setModelId(Long modelId) {
        this.modelId=modelId;
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

    public void setStatus(Byte status) {
        this.status=status;
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

    public ModelActions() {
        super();
    }
}