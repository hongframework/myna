package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Task;
import com.hframework.myna.config.domain.model.Task_Example;
import com.hframework.myna.config.dao.TaskMapper;
import com.hframework.myna.config.service.interfaces.ITaskSV;

@Service("iTaskSV")
public class TaskSVImpl  implements ITaskSV {

	@Resource
	private TaskMapper taskMapper;
  


    /**
    * 创建任务
    * @param task
    * @return
    * @throws Exception
    */
    public int create(Task task) throws Exception {
        return taskMapper.insertSelective(task);
    }

    /**
    * 批量维护任务
    * @param tasks
    * @return
    * @throws Exception
    */
    public int batchOperate(Task[] tasks) throws  Exception{
        int result = 0;
        if(tasks != null) {
            for (Task task : tasks) {
                if(task.getId() == null) {
                    result += this.create(task);
                }else {
                    result += this.update(task);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务
    * @param task
    * @return
    * @throws Exception
    */
    public int update(Task task) throws  Exception {
        return taskMapper.updateByPrimaryKeySelective(task);
    }

    /**
    * 通过查询对象更新任务
    * @param task
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Task task, Task_Example example) throws  Exception {
        return taskMapper.updateByExampleSelective(task, example);
    }

    /**
    * 删除任务
    * @param task
    * @return
    * @throws Exception
    */
    public int delete(Task task) throws  Exception {
        return taskMapper.deleteByPrimaryKey(task.getId());
    }

    /**
    * 删除任务
    * @param taskId
    * @return
    * @throws Exception
    */
    public int delete(long taskId) throws  Exception {
        return taskMapper.deleteByPrimaryKey(taskId);
    }

    /**
    * 查找所有任务
    * @return
    */
    public List<Task> getTaskAll()  throws  Exception {
        return taskMapper.selectByExample(new Task_Example());
    }

    /**
    * 通过任务ID查询任务
    * @param taskId
    * @return
    * @throws Exception
    */
    public Task getTaskByPK(long taskId)  throws  Exception {
        return taskMapper.selectByPrimaryKey(taskId);
    }


    /**
    * 通过MAP参数查询任务
    * @param params
    * @return
    * @throws Exception
    */
    public List<Task> getTaskListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务
    * @param example
    * @return
    * @throws Exception
    */
    public List<Task> getTaskListByExample(Task_Example example) throws  Exception {
        return taskMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getTaskCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getTaskCountByExample(Task_Example example) throws  Exception {
        return taskMapper.countByExample(example);
    }


  	//getter
 	
	public TaskMapper getTaskMapper(){
		return taskMapper;
	}
	//setter
	public void setTaskMapper(TaskMapper taskMapper){
    	this.taskMapper = taskMapper;
    }
}
