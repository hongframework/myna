package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.TaskActions;
import com.hframework.myna.config.domain.model.TaskActions_Example;
import com.hframework.myna.config.dao.TaskActionsMapper;
import com.hframework.myna.config.service.interfaces.ITaskActionsSV;

@Service("iTaskActionsSV")
public class TaskActionsSVImpl  implements ITaskActionsSV {

	@Resource
	private TaskActionsMapper taskActionsMapper;
  


    /**
    * 创建任务行为
    * @param taskActions
    * @return
    * @throws Exception
    */
    public int create(TaskActions taskActions) throws Exception {
        return taskActionsMapper.insertSelective(taskActions);
    }

    /**
    * 批量维护任务行为
    * @param taskActionss
    * @return
    * @throws Exception
    */
    public int batchOperate(TaskActions[] taskActionss) throws  Exception{
        int result = 0;
        if(taskActionss != null) {
            for (TaskActions taskActions : taskActionss) {
                if(taskActions.getId() == null) {
                    result += this.create(taskActions);
                }else {
                    result += this.update(taskActions);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务行为
    * @param taskActions
    * @return
    * @throws Exception
    */
    public int update(TaskActions taskActions) throws  Exception {
        return taskActionsMapper.updateByPrimaryKeySelective(taskActions);
    }

    /**
    * 通过查询对象更新任务行为
    * @param taskActions
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(TaskActions taskActions, TaskActions_Example example) throws  Exception {
        return taskActionsMapper.updateByExampleSelective(taskActions, example);
    }

    /**
    * 删除任务行为
    * @param taskActions
    * @return
    * @throws Exception
    */
    public int delete(TaskActions taskActions) throws  Exception {
        return taskActionsMapper.deleteByPrimaryKey(taskActions.getId());
    }

    /**
    * 删除任务行为
    * @param taskActionsId
    * @return
    * @throws Exception
    */
    public int delete(long taskActionsId) throws  Exception {
        return taskActionsMapper.deleteByPrimaryKey(taskActionsId);
    }

    /**
    * 查找所有任务行为
    * @return
    */
    public List<TaskActions> getTaskActionsAll()  throws  Exception {
        return taskActionsMapper.selectByExample(new TaskActions_Example());
    }

    /**
    * 通过任务行为ID查询任务行为
    * @param taskActionsId
    * @return
    * @throws Exception
    */
    public TaskActions getTaskActionsByPK(long taskActionsId)  throws  Exception {
        return taskActionsMapper.selectByPrimaryKey(taskActionsId);
    }


    /**
    * 通过MAP参数查询任务行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<TaskActions> getTaskActionsListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<TaskActions> getTaskActionsListByExample(TaskActions_Example example) throws  Exception {
        return taskActionsMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getTaskActionsCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getTaskActionsCountByExample(TaskActions_Example example) throws  Exception {
        return taskActionsMapper.countByExample(example);
    }


  	//getter
 	
	public TaskActionsMapper getTaskActionsMapper(){
		return taskActionsMapper;
	}
	//setter
	public void setTaskActionsMapper(TaskActionsMapper taskActionsMapper){
    	this.taskActionsMapper = taskActionsMapper;
    }
}
