package com.hframework.myna.config.domain.model;

import java.util.Date;

public class TaskActions {
    private Long id;

    private String actionTriggerValue;

    private Long taskId;

    private Long actionId;

    private String utterances;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public TaskActions(Long id, String actionTriggerValue, Long taskId, Long actionId, String utterances, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.actionTriggerValue = actionTriggerValue;
        this.taskId = taskId;
        this.actionId = actionId;
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

    public String getActionTriggerValue() {
        return actionTriggerValue;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Long getActionId() {
        return actionId;
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

    public void setActionTriggerValue(String actionTriggerValue) {
        this.actionTriggerValue=actionTriggerValue;
    }

    public void setTaskId(Long taskId) {
        this.taskId=taskId;
    }

    public void setActionId(Long actionId) {
        this.actionId=actionId;
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

    public TaskActions() {
        super();
    }
}