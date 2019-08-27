package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Robot {
    private Long id;

    private String code;

    private String name;

    private String logo;

    private Byte type;

    private String description;

    private String services;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public Robot(Long id, String code, String name, String logo, Byte type, String description, String services, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.logo = logo;
        this.type = type;
        this.description = description;
        this.services = services;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
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

    public String getLogo() {
        return logo;
    }

    public Byte getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getServices() {
        return services;
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

    public void setCode(String code) {
        this.code=code;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setLogo(String logo) {
        this.logo=logo;
    }

    public void setType(Byte type) {
        this.type=type;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setServices(String services) {
        this.services=services;
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

    public Robot() {
        super();
    }
}