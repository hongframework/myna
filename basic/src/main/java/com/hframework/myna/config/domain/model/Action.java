package com.hframework.myna.config.domain.model;

import java.util.Date;

public class Action {
    private Long id;

    private String code;

    private String name;

    private String description;

    private Byte type;

    private Byte actionScope;

    private String triggerResult;

    private Long defaultUtteranceId;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Byte triggerCase;

    private String triggerDefaultValue;

    private Byte triggerValueType;

    public Action(Long id, String code, String name, String description, Byte type, Byte actionScope, String triggerResult, Long defaultUtteranceId, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, Byte triggerCase, String triggerDefaultValue, Byte triggerValueType) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.type = type;
        this.actionScope = actionScope;
        this.triggerResult = triggerResult;
        this.defaultUtteranceId = defaultUtteranceId;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.triggerCase = triggerCase;
        this.triggerDefaultValue = triggerDefaultValue;
        this.triggerValueType = triggerValueType;
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

    public String getDescription() {
        return description;
    }

    public Byte getType() {
        return type;
    }

    public Byte getActionScope() {
        return actionScope;
    }

    public String getTriggerResult() {
        return triggerResult;
    }

    public Long getDefaultUtteranceId() {
        return defaultUtteranceId;
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

    public Byte getTriggerCase() {
        return triggerCase;
    }

    public String getTriggerDefaultValue() {
        return triggerDefaultValue;
    }

    public Byte getTriggerValueType() {
        return triggerValueType;
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

    public void setDescription(String description) {
        this.description=description;
    }

    public void setType(Byte type) {
        this.type=type;
    }

    public void setActionScope(Byte actionScope) {
        this.actionScope=actionScope;
    }

    public void setTriggerResult(String triggerResult) {
        this.triggerResult=triggerResult;
    }

    public void setDefaultUtteranceId(Long defaultUtteranceId) {
        this.defaultUtteranceId=defaultUtteranceId;
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

    public void setTriggerCase(Byte triggerCase) {
        this.triggerCase=triggerCase;
    }

    public void setTriggerDefaultValue(String triggerDefaultValue) {
        this.triggerDefaultValue=triggerDefaultValue;
    }

    public void setTriggerValueType(Byte triggerValueType) {
        this.triggerValueType=triggerValueType;
    }

    public Action() {
        super();
    }
}