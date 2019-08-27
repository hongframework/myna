package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Robot;
import com.hframework.myna.config.domain.model.Robot_Example;


public interface IRobotSV   {

  
    /**
    * 创建机器人
    * @param robot
    * @return
    * @throws Exception
    */
    public int create(Robot robot) throws  Exception;

    /**
    * 批量维护机器人
    * @param robots
    * @return
    * @throws Exception
    */
    public int batchOperate(Robot[] robots) throws  Exception;
    /**
    * 更新机器人
    * @param robot
    * @return
    * @throws Exception
    */
    public int update(Robot robot) throws  Exception;

    /**
    * 通过查询对象更新机器人
    * @param robot
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Robot robot, Robot_Example example) throws  Exception;

    /**
    * 删除机器人
    * @param robot
    * @return
    * @throws Exception
    */
    public int delete(Robot robot) throws  Exception;


    /**
    * 删除机器人
    * @param robotId
    * @return
    * @throws Exception
    */
    public int delete(long robotId) throws  Exception;


    /**
    * 查找所有机器人
    * @return
    */
    public List<Robot> getRobotAll()  throws  Exception;

    /**
    * 通过机器人ID查询机器人
    * @param robotId
    * @return
    * @throws Exception
    */
    public Robot getRobotByPK(long robotId)  throws  Exception;

    /**
    * 通过MAP参数查询机器人
    * @param params
    * @return
    * @throws Exception
    */
    public List<Robot> getRobotListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询机器人
    * @param example
    * @return
    * @throws Exception
    */
    public List<Robot> getRobotListByExample(Robot_Example example) throws  Exception;


    /**
    * 通过MAP参数查询机器人数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getRobotCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询机器人数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getRobotCountByExample(Robot_Example example) throws  Exception;


 }
