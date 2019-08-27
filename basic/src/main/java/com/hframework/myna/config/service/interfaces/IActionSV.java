package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Action;
import com.hframework.myna.config.domain.model.Action_Example;


public interface IActionSV   {

  
    /**
    * 创建行为
    * @param action
    * @return
    * @throws Exception
    */
    public int create(Action action) throws  Exception;

    /**
    * 批量维护行为
    * @param actions
    * @return
    * @throws Exception
    */
    public int batchOperate(Action[] actions) throws  Exception;
    /**
    * 更新行为
    * @param action
    * @return
    * @throws Exception
    */
    public int update(Action action) throws  Exception;

    /**
    * 通过查询对象更新行为
    * @param action
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Action action, Action_Example example) throws  Exception;

    /**
    * 删除行为
    * @param action
    * @return
    * @throws Exception
    */
    public int delete(Action action) throws  Exception;


    /**
    * 删除行为
    * @param actionId
    * @return
    * @throws Exception
    */
    public int delete(long actionId) throws  Exception;


    /**
    * 查找所有行为
    * @return
    */
    public List<Action> getActionAll()  throws  Exception;

    /**
    * 通过行为ID查询行为
    * @param actionId
    * @return
    * @throws Exception
    */
    public Action getActionByPK(long actionId)  throws  Exception;

    /**
    * 通过MAP参数查询行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<Action> getActionListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<Action> getActionListByExample(Action_Example example) throws  Exception;


    /**
    * 通过MAP参数查询行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getActionCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getActionCountByExample(Action_Example example) throws  Exception;


 }
