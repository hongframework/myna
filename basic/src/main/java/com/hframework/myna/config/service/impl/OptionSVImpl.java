package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Option;
import com.hframework.myna.config.domain.model.Option_Example;
import com.hframework.myna.config.dao.OptionMapper;
import com.hframework.myna.config.service.interfaces.IOptionSV;

@Service("iOptionSV")
public class OptionSVImpl  implements IOptionSV {

	@Resource
	private OptionMapper optionMapper;
  


    /**
    * 创建选项
    * @param option
    * @return
    * @throws Exception
    */
    public int create(Option option) throws Exception {
        return optionMapper.insertSelective(option);
    }

    /**
    * 批量维护选项
    * @param options
    * @return
    * @throws Exception
    */
    public int batchOperate(Option[] options) throws  Exception{
        int result = 0;
        if(options != null) {
            for (Option option : options) {
                if(option.getId() == null) {
                    result += this.create(option);
                }else {
                    result += this.update(option);
                }
            }
        }
        return result;
    }

    /**
    * 更新选项
    * @param option
    * @return
    * @throws Exception
    */
    public int update(Option option) throws  Exception {
        return optionMapper.updateByPrimaryKeySelective(option);
    }

    /**
    * 通过查询对象更新选项
    * @param option
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Option option, Option_Example example) throws  Exception {
        return optionMapper.updateByExampleSelective(option, example);
    }

    /**
    * 删除选项
    * @param option
    * @return
    * @throws Exception
    */
    public int delete(Option option) throws  Exception {
        return optionMapper.deleteByPrimaryKey(option.getId());
    }

    /**
    * 删除选项
    * @param optionId
    * @return
    * @throws Exception
    */
    public int delete(long optionId) throws  Exception {
        return optionMapper.deleteByPrimaryKey(optionId);
    }

    /**
    * 查找所有选项
    * @return
    */
    public List<Option> getOptionAll()  throws  Exception {
        return optionMapper.selectByExample(new Option_Example());
    }

    /**
    * 通过选项ID查询选项
    * @param optionId
    * @return
    * @throws Exception
    */
    public Option getOptionByPK(long optionId)  throws  Exception {
        return optionMapper.selectByPrimaryKey(optionId);
    }


    /**
    * 通过MAP参数查询选项
    * @param params
    * @return
    * @throws Exception
    */
    public List<Option> getOptionListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询选项
    * @param example
    * @return
    * @throws Exception
    */
    public List<Option> getOptionListByExample(Option_Example example) throws  Exception {
        return optionMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询选项数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getOptionCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询选项数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getOptionCountByExample(Option_Example example) throws  Exception {
        return optionMapper.countByExample(example);
    }


  	//getter
 	
	public OptionMapper getOptionMapper(){
		return optionMapper;
	}
	//setter
	public void setOptionMapper(OptionMapper optionMapper){
    	this.optionMapper = optionMapper;
    }
}
