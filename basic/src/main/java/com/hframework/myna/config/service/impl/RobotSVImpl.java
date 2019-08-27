package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Robot;
import com.hframework.myna.config.domain.model.Robot_Example;
import com.hframework.myna.config.dao.RobotMapper;
import com.hframework.myna.config.service.interfaces.IRobotSV;

@Service("iRobotSV")
public class RobotSVImpl  implements IRobotSV {

	@Resource
	private RobotMapper robotMapper;
  


    /**
    * 创建机器人
    * @param robot
    * @return
    * @throws Exception
    */
    public int create(Robot robot) throws Exception {
        return robotMapper.insertSelective(robot);
    }

    /**
    * 批量维护机器人
    * @param robots
    * @return
    * @throws Exception
    */
    public int batchOperate(Robot[] robots) throws  Exception{
        int result = 0;
        if(robots != null) {
            for (Robot robot : robots) {
                if(robot.getId() == null) {
                    result += this.create(robot);
                }else {
                    result += this.update(robot);
                }
            }
        }
        return result;
    }

    /**
    * 更新机器人
    * @param robot
    * @return
    * @throws Exception
    */
    public int update(Robot robot) throws  Exception {
        return robotMapper.updateByPrimaryKeySelective(robot);
    }

    /**
    * 通过查询对象更新机器人
    * @param robot
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Robot robot, Robot_Example example) throws  Exception {
        return robotMapper.updateByExampleSelective(robot, example);
    }

    /**
    * 删除机器人
    * @param robot
    * @return
    * @throws Exception
    */
    public int delete(Robot robot) throws  Exception {
        return robotMapper.deleteByPrimaryKey(robot.getId());
    }

    /**
    * 删除机器人
    * @param robotId
    * @return
    * @throws Exception
    */
    public int delete(long robotId) throws  Exception {
        return robotMapper.deleteByPrimaryKey(robotId);
    }

    /**
    * 查找所有机器人
    * @return
    */
    public List<Robot> getRobotAll()  throws  Exception {
        return robotMapper.selectByExample(new Robot_Example());
    }

    /**
    * 通过机器人ID查询机器人
    * @param robotId
    * @return
    * @throws Exception
    */
    public Robot getRobotByPK(long robotId)  throws  Exception {
        return robotMapper.selectByPrimaryKey(robotId);
    }


    /**
    * 通过MAP参数查询机器人
    * @param params
    * @return
    * @throws Exception
    */
    public List<Robot> getRobotListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询机器人
    * @param example
    * @return
    * @throws Exception
    */
    public List<Robot> getRobotListByExample(Robot_Example example) throws  Exception {
        return robotMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询机器人数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getRobotCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询机器人数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getRobotCountByExample(Robot_Example example) throws  Exception {
        return robotMapper.countByExample(example);
    }


  	//getter
 	
	public RobotMapper getRobotMapper(){
		return robotMapper;
	}
	//setter
	public void setRobotMapper(RobotMapper robotMapper){
    	this.robotMapper = robotMapper;
    }
}
