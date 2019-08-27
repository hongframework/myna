package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Options;
import com.hframework.myna.config.domain.model.Options_Example;
import com.hframework.myna.config.dao.OptionsMapper;
import com.hframework.myna.config.service.interfaces.IOptionsSV;

@Service("iOptionsSV")
public class OptionsSVImpl  implements IOptionsSV {

	@Resource
	private OptionsMapper optionsMapper;
  


    /**
    * 创建选项集
    * @param options
    * @return
    * @throws Exception
    */
    public int create(Options options) throws Exception {
        return optionsMapper.insertSelective(options);
    }

    /**
    * 批量维护选项集
    * @param optionss
    * @return
    * @throws Exception
    */
    public int batchOperate(Options[] optionss) throws  Exception{
        int result = 0;
        if(optionss != null) {
            for (Options options : optionss) {
                if(options.getId() == null) {
                    result += this.create(options);
                }else {
                    result += this.update(options);
                }
            }
        }
        return result;
    }

    /**
    * 更新选项集
    * @param options
    * @return
    * @throws Exception
    */
    public int update(Options options) throws  Exception {
        return optionsMapper.updateByPrimaryKeySelective(options);
    }

    /**
    * 通过查询对象更新选项集
    * @param options
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Options options, Options_Example example) throws  Exception {
        return optionsMapper.updateByExampleSelective(options, example);
    }

    /**
    * 删除选项集
    * @param options
    * @return
    * @throws Exception
    */
    public int delete(Options options) throws  Exception {
        return optionsMapper.deleteByPrimaryKey(options.getId());
    }

    /**
    * 删除选项集
    * @param optionsId
    * @return
    * @throws Exception
    */
    public int delete(long optionsId) throws  Exception {
        return optionsMapper.deleteByPrimaryKey(optionsId);
    }

    /**
    * 查找所有选项集
    * @return
    */
    public List<Options> getOptionsAll()  throws  Exception {
        return optionsMapper.selectByExample(new Options_Example());
    }

    /**
    * 通过选项集ID查询选项集
    * @param optionsId
    * @return
    * @throws Exception
    */
    public Options getOptionsByPK(long optionsId)  throws  Exception {
        return optionsMapper.selectByPrimaryKey(optionsId);
    }


    /**
    * 通过MAP参数查询选项集
    * @param params
    * @return
    * @throws Exception
    */
    public List<Options> getOptionsListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询选项集
    * @param example
    * @return
    * @throws Exception
    */
    public List<Options> getOptionsListByExample(Options_Example example) throws  Exception {
        return optionsMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询选项集数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getOptionsCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询选项集数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getOptionsCountByExample(Options_Example example) throws  Exception {
        return optionsMapper.countByExample(example);
    }


  	//getter
 	
	public OptionsMapper getOptionsMapper(){
		return optionsMapper;
	}
	//setter
	public void setOptionsMapper(OptionsMapper optionsMapper){
    	this.optionsMapper = optionsMapper;
    }
}
