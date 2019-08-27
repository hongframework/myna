package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Option {
    private Long id;

    private String name;

    private Long utility;

    private Long optionsId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte status;

    public Option(Long id, String name, Long utility, Long optionsId, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte status) {
        this.id = id;
        this.name = name;
        this.utility = utility;
        this.optionsId = optionsId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getUtility() {
        return utility;
    }

    public Long getOptionsId() {
        return optionsId;
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

    public void setId(Long id) {
        this.id=id;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setUtility(Long utility) {
        this.utility=utility;
    }

    public void setOptionsId(Long optionsId) {
        this.optionsId=optionsId;
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

    public Option() {
        super();
    }
}