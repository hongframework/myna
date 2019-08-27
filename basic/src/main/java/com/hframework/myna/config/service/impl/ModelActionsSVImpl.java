package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.ModelActions;
import com.hframework.myna.config.domain.model.ModelActions_Example;
import com.hframework.myna.config.dao.ModelActionsMapper;
import com.hframework.myna.config.service.interfaces.IModelActionsSV;

@Service("iModelActionsSV")
public class ModelActionsSVImpl  implements IModelActionsSV {

	@Resource
	private ModelActionsMapper modelActionsMapper;
  


    /**
    * 创建模式关联行为
    * @param modelActions
    * @return
    * @throws Exception
    */
    public int create(ModelActions modelActions) throws Exception {
        return modelActionsMapper.insertSelective(modelActions);
    }

    /**
    * 批量维护模式关联行为
    * @param modelActionss
    * @return
    * @throws Exception
    */
    public int batchOperate(ModelActions[] modelActionss) throws  Exception{
        int result = 0;
        if(modelActionss != null) {
            for (ModelActions modelActions : modelActionss) {
                if(modelActions.getId() == null) {
                    result += this.create(modelActions);
                }else {
                    result += this.update(modelActions);
                }
            }
        }
        return result;
    }

    /**
    * 更新模式关联行为
    * @param modelActions
    * @return
    * @throws Exception
    */
    public int update(ModelActions modelActions) throws  Exception {
        return modelActionsMapper.updateByPrimaryKeySelective(modelActions);
    }

    /**
    * 通过查询对象更新模式关联行为
    * @param modelActions
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(ModelActions modelActions, ModelActions_Example example) throws  Exception {
        return modelActionsMapper.updateByExampleSelective(modelActions, example);
    }

    /**
    * 删除模式关联行为
    * @param modelActions
    * @return
    * @throws Exception
    */
    public int delete(ModelActions modelActions) throws  Exception {
        return modelActionsMapper.deleteByPrimaryKey(modelActions.getId());
    }

    /**
    * 删除模式关联行为
    * @param modelActionsId
    * @return
    * @throws Exception
    */
    public int delete(long modelActionsId) throws  Exception {
        return modelActionsMapper.deleteByPrimaryKey(modelActionsId);
    }

    /**
    * 查找所有模式关联行为
    * @return
    */
    public List<ModelActions> getModelActionsAll()  throws  Exception {
        return modelActionsMapper.selectByExample(new ModelActions_Example());
    }

    /**
    * 通过模式关联行为ID查询模式关联行为
    * @param modelActionsId
    * @return
    * @throws Exception
    */
    public ModelActions getModelActionsByPK(long modelActionsId)  throws  Exception {
        return modelActionsMapper.selectByPrimaryKey(modelActionsId);
    }


    /**
    * 通过MAP参数查询模式关联行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<ModelActions> getModelActionsListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询模式关联行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<ModelActions> getModelActionsListByExample(ModelActions_Example example) throws  Exception {
        return modelActionsMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询模式关联行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getModelActionsCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询模式关联行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getModelActionsCountByExample(ModelActions_Example example) throws  Exception {
        return modelActionsMapper.countByExample(example);
    }


  	//getter
 	
	public ModelActionsMapper getModelActionsMapper(){
		return modelActionsMapper;
	}
	//setter
	public void setModelActionsMapper(ModelActionsMapper modelActionsMapper){
    	this.modelActionsMapper = modelActionsMapper;
    }
}
