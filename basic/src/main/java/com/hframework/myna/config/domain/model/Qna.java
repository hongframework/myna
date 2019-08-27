package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Qna {
    private Long id;

    private String description;

    private String keyword;

    private Byte answerType;

    private Long optionsId;

    private String semanticParsers;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Long taskId;

    public Qna(Long id, String description, String keyword, Byte answerType, Long optionsId, String semanticParsers, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Long taskId) {
        this.id = id;
        this.description = description;
        this.keyword = keyword;
        this.answerType = answerType;
        this.optionsId = optionsId;
        this.semanticParsers = semanticParsers;
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

    public String getDescription() {
        return description;
    }

    public String getKeyword() {
        return keyword;
    }

    public Byte getAnswerType() {
        return answerType;
    }

    public Long getOptionsId() {
        return optionsId;
    }

    public String getSemanticParsers() {
        return semanticParsers;
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

    public void setDescription(String description) {
        this.description=description;
    }

    public void setKeyword(String keyword) {
        this.keyword=keyword;
    }

    public void setAnswerType(Byte answerType) {
        this.answerType=answerType;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId=optionsId;
    }

    public void setSemanticParsers(String semanticParsers) {
        this.semanticParsers=semanticParsers;
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

    public Qna() {
        super();
    }
}