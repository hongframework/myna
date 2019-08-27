package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Model;
import com.hframework.myna.config.domain.model.Model_Example;
import com.hframework.myna.config.dao.ModelMapper;
import com.hframework.myna.config.service.interfaces.IModelSV;

@Service("iModelSV")
public class ModelSVImpl  implements IModelSV {

	@Resource
	private ModelMapper modelMapper;
  


    /**
    * 创建工作模式
    * @param model
    * @return
    * @throws Exception
    */
    public int create(Model model) throws Exception {
        return modelMapper.insertSelective(model);
    }

    /**
    * 批量维护工作模式
    * @param models
    * @return
    * @throws Exception
    */
    public int batchOperate(Model[] models) throws  Exception{
        int result = 0;
        if(models != null) {
            for (Model model : models) {
                if(model.getId() == null) {
                    result += this.create(model);
                }else {
                    result += this.update(model);
                }
            }
        }
        return result;
    }

    /**
    * 更新工作模式
    * @param model
    * @return
    * @throws Exception
    */
    public int update(Model model) throws  Exception {
        return modelMapper.updateByPrimaryKeySelective(model);
    }

    /**
    * 通过查询对象更新工作模式
    * @param model
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Model model, Model_Example example) throws  Exception {
        return modelMapper.updateByExampleSelective(model, example);
    }

    /**
    * 删除工作模式
    * @param model
    * @return
    * @throws Exception
    */
    public int delete(Model model) throws  Exception {
        return modelMapper.deleteByPrimaryKey(model.getId());
    }

    /**
    * 删除工作模式
    * @param modelId
    * @return
    * @throws Exception
    */
    public int delete(long modelId) throws  Exception {
        return modelMapper.deleteByPrimaryKey(modelId);
    }

    /**
    * 查找所有工作模式
    * @return
    */
    public List<Model> getModelAll()  throws  Exception {
        return modelMapper.selectByExample(new Model_Example());
    }

    /**
    * 通过工作模式ID查询工作模式
    * @param modelId
    * @return
    * @throws Exception
    */
    public Model getModelByPK(long modelId)  throws  Exception {
        return modelMapper.selectByPrimaryKey(modelId);
    }


    /**
    * 通过MAP参数查询工作模式
    * @param params
    * @return
    * @throws Exception
    */
    public List<Model> getModelListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询工作模式
    * @param example
    * @return
    * @throws Exception
    */
    public List<Model> getModelListByExample(Model_Example example) throws  Exception {
        return modelMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询工作模式数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getModelCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询工作模式数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getModelCountByExample(Model_Example example) throws  Exception {
        return modelMapper.countByExample(example);
    }


  	//getter
 	
	public ModelMapper getModelMapper(){
		return modelMapper;
	}
	//setter
	public void setModelMapper(ModelMapper modelMapper){
    	this.modelMapper = modelMapper;
    }
}
