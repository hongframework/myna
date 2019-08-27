package com.hframework.myna.config.domain.model;

import java.util.Date;

public class SyntaxDef {
    private Long id;

    private Byte type;

    private String expression;

    private String keywords;

    private String limits;

    private String example;

    private String name;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    public SyntaxDef(Long id, Byte type, String expression, String keywords, String limits, String example, String name, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime) {
        this.id = id;
        this.type = type;
        this.expression = expression;
        this.keywords = keywords;
        this.limits = limits;
        this.example = example;
        this.name = name;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
    }

    public Long getId() {
        return id;
    }

    public Byte getType() {
        return type;
    }

    public String getExpression() {
        return expression;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getLimits() {
        return limits;
    }

    public String getExample() {
        return example;
    }

    public String getName() {
        return name;
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

    public void setType(Byte type) {
        this.type=type;
    }

    public void setExpression(String expression) {
        this.expression=expression;
    }

    public void setKeywords(String keywords) {
        this.keywords=keywords;
    }

    public void setLimits(String limits) {
        this.limits=limits;
    }

    public void setExample(String example) {
        this.example=example;
    }

    public void setName(String name) {
        this.name=name;
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

    public SyntaxDef() {
        super();
    }
}