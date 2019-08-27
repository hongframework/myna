package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Corpus {
    private Long id;

    private String content;

    private Byte slotNumbers;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Long taskId;

    public Corpus(Long id, String content, Byte slotNumbers, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Long taskId) {
        this.id = id;
        this.content = content;
        this.slotNumbers = slotNumbers;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Byte getSlotNumbers() {
        return slotNumbers;
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

    public Long getTaskId() {
        return taskId;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setContent(String content) {
        this.content=content;
    }

    public void setSlotNumbers(Byte slotNumbers) {
        this.slotNumbers=slotNumbers;
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

    public void setTaskId(Long taskId) {
        this.taskId=taskId;
    }

    public Corpus() {
        super();
    }
}