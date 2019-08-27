package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.TaskVersion;
import com.hframework.myna.config.domain.model.TaskVersion_Example;


public interface ITaskVersionSV   {

  
    /**
    * 创建任务版本信息
    * @param taskVersion
    * @return
    * @throws Exception
    */
    public int create(TaskVersion taskVersion) throws  Exception;

    /**
    * 批量维护任务版本信息
    * @param taskVersions
    * @return
    * @throws Exception
    */
    public int batchOperate(TaskVersion[] taskVersions) throws  Exception;
    /**
    * 更新任务版本信息
    * @param taskVersion
    * @return
    * @throws Exception
    */
    public int update(TaskVersion taskVersion) throws  Exception;

    /**
    * 通过查询对象更新任务版本信息
    * @param taskVersion
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(TaskVersion taskVersion, TaskVersion_Example example) throws  Exception;

    /**
    * 删除任务版本信息
    * @param taskVersion
    * @return
    * @throws Exception
    */
    public int delete(TaskVersion taskVersion) throws  Exception;


    /**
    * 删除任务版本信息
    * @param taskVersionId
    * @return
    * @throws Exception
    */
    public int delete(long taskVersionId) throws  Exception;


    /**
    * 查找所有任务版本信息
    * @return
    */
    public List<TaskVersion> getTaskVersionAll()  throws  Exception;

    /**
    * 通过任务版本信息ID查询任务版本信息
    * @param taskVersionId
    * @return
    * @throws Exception
    */
    public TaskVersion getTaskVersionByPK(long taskVersionId)  throws  Exception;

    /**
    * 通过MAP参数查询任务版本信息
    * @param params
    * @return
    * @throws Exception
    */
    public List<TaskVersion> getTaskVersionListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务版本信息
    * @param example
    * @return
    * @throws Exception
    */
    public List<TaskVersion> getTaskVersionListByExample(TaskVersion_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务版本信息数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getTaskVersionCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务版本信息数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getTaskVersionCountByExample(TaskVersion_Example example) throws  Exception;


 }
