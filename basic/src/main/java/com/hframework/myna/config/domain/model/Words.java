package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Words {
    private Long id;

    private String name;

    private Byte status;

    private Long thesaurusId;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public Words(Long id, String name, Byte status, Long thesaurusId, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.thesaurusId = thesaurusId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Byte getStatus() {
        return status;
    }

    public Long getThesaurusId() {
        return thesaurusId;
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

    public void setName(String name) {
        this.name=name;
    }

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setThesaurusId(Long thesaurusId) {
        this.thesaurusId=thesaurusId;
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

    public Words() {
        super();
    }
}