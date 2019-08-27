package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Utterance {
    private Long id;

    private String content;

    private String resourceId;

    private Long actionId;

    private Long taskId;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public Utterance(Long id, String content, String resourceId, Long actionId, Long taskId, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.content = content;
        this.resourceId = resourceId;
        this.actionId = actionId;
        this.taskId = taskId;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getResourceId() {
        return resourceId;
    }

    public Long getActionId() {
        return actionId;
    }

    public Long getTaskId() {
        return taskId;
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

    public void setContent(String content) {
        this.content=content;
    }

    public void setResourceId(String resourceId) {
        this.resourceId=resourceId;
    }

    public void setActionId(Long actionId) {
        this.actionId=actionId;
    }

    public void setTaskId(Long taskId) {
        this.taskId=taskId;
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

    public Utterance() {
        super();
    }
}