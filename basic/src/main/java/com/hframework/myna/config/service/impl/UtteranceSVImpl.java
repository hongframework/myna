package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Utterance;
import com.hframework.myna.config.domain.model.Utterance_Example;
import com.hframework.myna.config.dao.UtteranceMapper;
import com.hframework.myna.config.service.interfaces.IUtteranceSV;

@Service("iUtteranceSV")
public class UtteranceSVImpl  implements IUtteranceSV {

	@Resource
	private UtteranceMapper utteranceMapper;
  


    /**
    * 创建话术
    * @param utterance
    * @return
    * @throws Exception
    */
    public int create(Utterance utterance) throws Exception {
        return utteranceMapper.insertSelective(utterance);
    }

    /**
    * 批量维护话术
    * @param utterances
    * @return
    * @throws Exception
    */
    public int batchOperate(Utterance[] utterances) throws  Exception{
        int result = 0;
        if(utterances != null) {
            for (Utterance utterance : utterances) {
                if(utterance.getId() == null) {
                    result += this.create(utterance);
                }else {
                    result += this.update(utterance);
                }
            }
        }
        return result;
    }

    /**
    * 更新话术
    * @param utterance
    * @return
    * @throws Exception
    */
    public int update(Utterance utterance) throws  Exception {
        return utteranceMapper.updateByPrimaryKeySelective(utterance);
    }

    /**
    * 通过查询对象更新话术
    * @param utterance
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Utterance utterance, Utterance_Example example) throws  Exception {
        return utteranceMapper.updateByExampleSelective(utterance, example);
    }

    /**
    * 删除话术
    * @param utterance
    * @return
    * @throws Exception
    */
    public int delete(Utterance utterance) throws  Exception {
        return utteranceMapper.deleteByPrimaryKey(utterance.getId());
    }

    /**
    * 删除话术
    * @param utteranceId
    * @return
    * @throws Exception
    */
    public int delete(long utteranceId) throws  Exception {
        return utteranceMapper.deleteByPrimaryKey(utteranceId);
    }

    /**
    * 查找所有话术
    * @return
    */
    public List<Utterance> getUtteranceAll()  throws  Exception {
        return utteranceMapper.selectByExample(new Utterance_Example());
    }

    /**
    * 通过话术ID查询话术
    * @param utteranceId
    * @return
    * @throws Exception
    */
    public Utterance getUtteranceByPK(long utteranceId)  throws  Exception {
        return utteranceMapper.selectByPrimaryKey(utteranceId);
    }


    /**
    * 通过MAP参数查询话术
    * @param params
    * @return
    * @throws Exception
    */
    public List<Utterance> getUtteranceListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询话术
    * @param example
    * @return
    * @throws Exception
    */
    public List<Utterance> getUtteranceListByExample(Utterance_Example example) throws  Exception {
        return utteranceMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询话术数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getUtteranceCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询话术数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getUtteranceCountByExample(Utterance_Example example) throws  Exception {
        return utteranceMapper.countByExample(example);
    }


  	//getter
 	
	public UtteranceMapper getUtteranceMapper(){
		return utteranceMapper;
	}
	//setter
	public void setUtteranceMapper(UtteranceMapper utteranceMapper){
    	this.utteranceMapper = utteranceMapper;
    }
}
