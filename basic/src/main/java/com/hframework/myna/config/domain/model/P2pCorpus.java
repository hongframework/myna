package com.hframework.myna.config.domain.model;

import java.util.Date;

public class P2pCorpus {
    private Long id;

    private Byte matchingType;

    private String corpuss;

    private Long p2pRepayId;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public P2pCorpus(Long id, Byte matchingType, String corpuss, Long p2pRepayId, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.matchingType = matchingType;
        this.corpuss = corpuss;
        this.p2pRepayId = p2pRepayId;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Byte getMatchingType() {
        return matchingType;
    }

    public String getCorpuss() {
        return corpuss;
    }

    public Long getP2pRepayId() {
        return p2pRepayId;
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

    public void setMatchingType(Byte matchingType) {
        this.matchingType=matchingType;
    }

    public void setCorpuss(String corpuss) {
        this.corpuss=corpuss;
    }

    public void setP2pRepayId(Long p2pRepayId) {
        this.p2pRepayId=p2pRepayId;
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

    public P2pCorpus() {
        super();
    }
}