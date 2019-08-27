package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Task;
import com.hframework.myna.config.domain.model.Task_Example;


public interface ITaskSV   {

  
    /**
    * 创建任务
    * @param task
    * @return
    * @throws Exception
    */
    public int create(Task task) throws  Exception;

    /**
    * 批量维护任务
    * @param tasks
    * @return
    * @throws Exception
    */
    public int batchOperate(Task[] tasks) throws  Exception;
    /**
    * 更新任务
    * @param task
    * @return
    * @throws Exception
    */
    public int update(Task task) throws  Exception;

    /**
    * 通过查询对象更新任务
    * @param task
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Task task, Task_Example example) throws  Exception;

    /**
    * 删除任务
    * @param task
    * @return
    * @throws Exception
    */
    public int delete(Task task) throws  Exception;


    /**
    * 删除任务
    * @param taskId
    * @return
    * @throws Exception
    */
    public int delete(long taskId) throws  Exception;


    /**
    * 查找所有任务
    * @return
    */
    public List<Task> getTaskAll()  throws  Exception;

    /**
    * 通过任务ID查询任务
    * @param taskId
    * @return
    * @throws Exception
    */
    public Task getTaskByPK(long taskId)  throws  Exception;

    /**
    * 通过MAP参数查询任务
    * @param params
    * @return
    * @throws Exception
    */
    public List<Task> getTaskListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务
    * @param example
    * @return
    * @throws Exception
    */
    public List<Task> getTaskListByExample(Task_Example example) throws  Exception;


    /**
    * 通过MAP参数查询任务数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getTaskCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询任务数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getTaskCountByExample(Task_Example example) throws  Exception;


 }
