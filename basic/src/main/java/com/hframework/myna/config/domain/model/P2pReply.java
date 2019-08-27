package com.hframework.myna.config.domain.model;

import java.util.Date;

public class P2pReply {
    private Long id;

    private String answer;

    private String title;

    private Byte type;

    private Byte status;

    private Long taskId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public P2pReply(Long id, String answer, String title, Byte type, Byte status, Long taskId, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.answer = answer;
        this.title = title;
        this.type = type;
        this.status = status;
        this.taskId = taskId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public String getTitle() {
        return title;
    }

    public Byte getType() {
        return type;
    }

    public Byte getStatus() {
        return status;
    }

    public Long getTaskId() {
        return taskId;
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

    public void setAnswer(String answer) {
        this.answer=answer;
    }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setType(Byte type) {
        this.type=type;
    }

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setTaskId(Long taskId) {
        this.taskId=taskId;
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

    public P2pReply() {
        super();
    }
}