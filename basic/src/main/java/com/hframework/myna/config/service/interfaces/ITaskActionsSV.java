package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.TaskActions;
import com.hframework.myna.config.domain.model.TaskActions_Example;


public interface ITaskActionsSV   {

  
    /**
    * 创建任务行为
    * @param taskActions
    * @return
    * @throws Exception
    */
    public int create(TaskActions taskActions) throws  Exception;

    /**
    * 批量维护任务行为
    * @param taskActionss
    * @return
    * @throws Exception
    */
    public int batchOperate(TaskActions[] taskActionss) throws  Exception;
    /**
    * 更新任务行为
    * @param taskActions
    * @return
    * @throws Exception
    */
    public int update(TaskActions taskActions) throws  Exception;

    /**
    * 通过查询对象更新任务行为
    * @param taskActions
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(TaskActions taskActions, TaskActions_Example example) throws  Exception;

    /**
    * 删除任务行为
    * @param taskActions
    * @return
    * @throws Exception
    */
    public int delete(TaskActions taskActions) throws  Exception;


    /**
    * 删除任务行为
    * @param taskActionsId
    * @return
    * @throws Exception
    */
    public int delete(long taskActionsId) throws  Exception;


    /**
    * 查找所有任务行为
    * @return
    */
    public List<TaskActions> getTaskActionsAll()  throws  Exception;

    /**
    * 通过任务行为ID查询任务行为
    * @param taskActionsId
    * @return
    * @throws Exception
    */
    public TaskActions getTaskActionsByPK(long taskActionsId)  throws  Exception;

    /**
    * 通过MAP参数查询任务行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<TaskActions> getTaskActionsListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<TaskActions> getTaskActionsListByExample(TaskActions_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getTaskActionsCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getTaskActionsCountByExample(TaskActions_Example example) throws  Exception;


 }
