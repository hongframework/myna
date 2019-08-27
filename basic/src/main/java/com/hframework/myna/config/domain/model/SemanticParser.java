package com.hframework.myna.config.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SemanticParser {
    private Long id;

    private String name;

    private Byte parseType;

    private Byte parseMethod;

    private String parseAddr;

    private Byte isDefault;

    private String description;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public SemanticParser(Long id, String name, Byte parseType, Byte parseMethod, String parseAddr, Byte isDefault, String description, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.name = name;
        this.parseType = parseType;
        this.parseMethod = parseMethod;
        this.parseAddr = parseAddr;
        this.isDefault = isDefault;
        this.description = description;
        this.status = status;
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

    public Byte getParseType() {
        return parseType;
    }

    public Byte getParseMethod() {
        return parseMethod;
    }

    public String getParseAddr() {
        return parseAddr;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public String getDescription() {
        return description;
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

    public void setName(String name) {
        this.name=name;
    }

    public void setParseType(Byte parseType) {
        this.parseType=parseType;
    }

    public void setParseMethod(Byte parseMethod) {
        this.parseMethod=parseMethod;
    }

    public void setParseAddr(String parseAddr) {
        this.parseAddr=parseAddr;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault=isDefault;
    }

    public void setDescription(String description) {
        this.description=description;
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

    public SemanticParser() {
        super();
    }
}