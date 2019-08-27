package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.TaskVersion;
import com.hframework.myna.config.domain.model.TaskVersion_Example;
import com.hframework.myna.config.dao.TaskVersionMapper;
import com.hframework.myna.config.service.interfaces.ITaskVersionSV;

@Service("iTaskVersionSV")
public class TaskVersionSVImpl  implements ITaskVersionSV {

	@Resource
	private TaskVersionMapper taskVersionMapper;
  


    /**
    * 创建任务版本信息
    * @param taskVersion
    * @return
    * @throws Exception
    */
    public int create(TaskVersion taskVersion) throws Exception {
        return taskVersionMapper.insertSelective(taskVersion);
    }

    /**
    * 批量维护任务版本信息
    * @param taskVersions
    * @return
    * @throws Exception
    */
    public int batchOperate(TaskVersion[] taskVersions) throws  Exception{
        int result = 0;
        if(taskVersions != null) {
            for (TaskVersion taskVersion : taskVersions) {
                if(taskVersion.getId() == null) {
                    result += this.create(taskVersion);
                }else {
                    result += this.update(taskVersion);
                }
            }
        }
        return result;
    }

    /**
    * 更新任务版本信息
    * @param taskVersion
    * @return
    * @throws Exception
    */
    public int update(TaskVersion taskVersion) throws  Exception {
        return taskVersionMapper.updateByPrimaryKeySelective(taskVersion);
    }

    /**
    * 通过查询对象更新任务版本信息
    * @param taskVersion
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(TaskVersion taskVersion, TaskVersion_Example example) throws  Exception {
        return taskVersionMapper.updateByExampleSelective(taskVersion, example);
    }

    /**
    * 删除任务版本信息
    * @param taskVersion
    * @return
    * @throws Exception
    */
    public int delete(TaskVersion taskVersion) throws  Exception {
        return taskVersionMapper.deleteByPrimaryKey(taskVersion.getId());
    }

    /**
    * 删除任务版本信息
    * @param taskVersionId
    * @return
    * @throws Exception
    */
    public int delete(long taskVersionId) throws  Exception {
        return taskVersionMapper.deleteByPrimaryKey(taskVersionId);
    }

    /**
    * 查找所有任务版本信息
    * @return
    */
    public List<TaskVersion> getTaskVersionAll()  throws  Exception {
        return taskVersionMapper.selectByExample(new TaskVersion_Example());
    }

    /**
    * 通过任务版本信息ID查询任务版本信息
    * @param taskVersionId
    * @return
    * @throws Exception
    */
    public TaskVersion getTaskVersionByPK(long taskVersionId)  throws  Exception {
        return taskVersionMapper.selectByPrimaryKey(taskVersionId);
    }


    /**
    * 通过MAP参数查询任务版本信息
    * @param params
    * @return
    * @throws Exception
    */
    public List<TaskVersion> getTaskVersionListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询任务版本信息
    * @param example
    * @return
    * @throws Exception
    */
    public List<TaskVersion> getTaskVersionListByExample(TaskVersion_Example example) throws  Exception {
        return taskVersionMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询任务版本信息数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getTaskVersionCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询任务版本信息数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getTaskVersionCountByExample(TaskVersion_Example example) throws  Exception {
        return taskVersionMapper.countByExample(example);
    }


  	//getter
 	
	public TaskVersionMapper getTaskVersionMapper(){
		return taskVersionMapper;
	}
	//setter
	public void setTaskVersionMapper(TaskVersionMapper taskVersionMapper){
    	this.taskVersionMapper = taskVersionMapper;
    }
}
