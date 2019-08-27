package com.hframework.myna.config.domain.model;

import java.util.Date;

public class TaskVersion {
    private Long id;

    private Long taskId;

    private String description;

    private Long creatorId;

    private Date createTime;

    private String descriptor;

    public TaskVersion(Long id, Long taskId, String description, Long creatorId, Date createTime, String descriptor) {
        this.id = id;
        this.taskId = taskId;
        this.description = description;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.descriptor = descriptor;
    }

    public Long getId() {
        return id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setTaskId(Long taskId) {
        this.taskId=taskId;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId=creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor=descriptor;
    }

    public TaskVersion() {
        super();
    }
}