package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Task {
    private Long id;

    private String code;

    private String name;

    private Long modelId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte status;

    private String closeUtterance;

    private String finalUtterance;

    private String welcomeUtterance;

    private Byte faqPattern;

    public Task(Long id, String code, String name, Long modelId, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte status, String closeUtterance, String finalUtterance, String welcomeUtterance, Byte faqPattern) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.modelId = modelId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.status = status;
        this.closeUtterance = closeUtterance;
        this.finalUtterance = finalUtterance;
        this.welcomeUtterance = welcomeUtterance;
        this.faqPattern = faqPattern;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getModelId() {
        return modelId;
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

    public Byte getStatus() {
        return status;
    }

    public String getCloseUtterance() {
        return closeUtterance;
    }

    public String getFinalUtterance() {
        return finalUtterance;
    }

    public String getWelcomeUtterance() {
        return welcomeUtterance;
    }

    public Byte getFaqPattern() {
        return faqPattern;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setCode(String code) {
        this.code=code;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setModelId(Long modelId) {
        this.modelId=modelId;
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

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setCloseUtterance(String closeUtterance) {
        this.closeUtterance=closeUtterance;
    }

    public void setFinalUtterance(String finalUtterance) {
        this.finalUtterance=finalUtterance;
    }

    public void setWelcomeUtterance(String welcomeUtterance) {
        this.welcomeUtterance=welcomeUtterance;
    }

    public void setFaqPattern(Byte faqPattern) {
        this.faqPattern=faqPattern;
    }

    public Task() {
        super();
    }
}