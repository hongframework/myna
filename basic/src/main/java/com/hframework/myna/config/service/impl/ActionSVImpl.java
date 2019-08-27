package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Action;
import com.hframework.myna.config.domain.model.Action_Example;
import com.hframework.myna.config.dao.ActionMapper;
import com.hframework.myna.config.service.interfaces.IActionSV;

@Service("iActionSV")
public class ActionSVImpl  implements IActionSV {

	@Resource
	private ActionMapper actionMapper;
  


    /**
    * 创建行为
    * @param action
    * @return
    * @throws Exception
    */
    public int create(Action action) throws Exception {
        return actionMapper.insertSelective(action);
    }

    /**
    * 批量维护行为
    * @param actions
    * @return
    * @throws Exception
    */
    public int batchOperate(Action[] actions) throws  Exception{
        int result = 0;
        if(actions != null) {
            for (Action action : actions) {
                if(action.getId() == null) {
                    result += this.create(action);
                }else {
                    result += this.update(action);
                }
            }
        }
        return result;
    }

    /**
    * 更新行为
    * @param action
    * @return
    * @throws Exception
    */
    public int update(Action action) throws  Exception {
        return actionMapper.updateByPrimaryKeySelective(action);
    }

    /**
    * 通过查询对象更新行为
    * @param action
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Action action, Action_Example example) throws  Exception {
        return actionMapper.updateByExampleSelective(action, example);
    }

    /**
    * 删除行为
    * @param action
    * @return
    * @throws Exception
    */
    public int delete(Action action) throws  Exception {
        return actionMapper.deleteByPrimaryKey(action.getId());
    }

    /**
    * 删除行为
    * @param actionId
    * @return
    * @throws Exception
    */
    public int delete(long actionId) throws  Exception {
        return actionMapper.deleteByPrimaryKey(actionId);
    }

    /**
    * 查找所有行为
    * @return
    */
    public List<Action> getActionAll()  throws  Exception {
        return actionMapper.selectByExample(new Action_Example());
    }

    /**
    * 通过行为ID查询行为
    * @param actionId
    * @return
    * @throws Exception
    */
    public Action getActionByPK(long actionId)  throws  Exception {
        return actionMapper.selectByPrimaryKey(actionId);
    }


    /**
    * 通过MAP参数查询行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<Action> getActionListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<Action> getActionListByExample(Action_Example example) throws  Exception {
        return actionMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getActionCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getActionCountByExample(Action_Example example) throws  Exception {
        return actionMapper.countByExample(example);
    }


  	//getter
 	
	public ActionMapper getActionMapper(){
		return actionMapper;
	}
	//setter
	public void setActionMapper(ActionMapper actionMapper){
    	this.actionMapper = actionMapper;
    }
}
