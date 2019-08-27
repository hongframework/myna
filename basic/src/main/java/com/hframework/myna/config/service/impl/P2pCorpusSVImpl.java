package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.P2pCorpus;
import com.hframework.myna.config.domain.model.P2pCorpus_Example;
import com.hframework.myna.config.dao.P2pCorpusMapper;
import com.hframework.myna.config.service.interfaces.IP2pCorpusSV;

@Service("iP2pCorpusSV")
public class P2pCorpusSVImpl  implements IP2pCorpusSV {

	@Resource
	private P2pCorpusMapper p2pCorpusMapper;
  


    /**
    * 创建点对点语料
    * @param p2pCorpus
    * @return
    * @throws Exception
    */
    public int create(P2pCorpus p2pCorpus) throws Exception {
        return p2pCorpusMapper.insertSelective(p2pCorpus);
    }

    /**
    * 批量维护点对点语料
    * @param p2pCorpuss
    * @return
    * @throws Exception
    */
    public int batchOperate(P2pCorpus[] p2pCorpuss) throws  Exception{
        int result = 0;
        if(p2pCorpuss != null) {
            for (P2pCorpus p2pCorpus : p2pCorpuss) {
                if(p2pCorpus.getId() == null) {
                    result += this.create(p2pCorpus);
                }else {
                    result += this.update(p2pCorpus);
                }
            }
        }
        return result;
    }

    /**
    * 更新点对点语料
    * @param p2pCorpus
    * @return
    * @throws Exception
    */
    public int update(P2pCorpus p2pCorpus) throws  Exception {
        return p2pCorpusMapper.updateByPrimaryKeySelective(p2pCorpus);
    }

    /**
    * 通过查询对象更新点对点语料
    * @param p2pCorpus
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(P2pCorpus p2pCorpus, P2pCorpus_Example example) throws  Exception {
        return p2pCorpusMapper.updateByExampleSelective(p2pCorpus, example);
    }

    /**
    * 删除点对点语料
    * @param p2pCorpus
    * @return
    * @throws Exception
    */
    public int delete(P2pCorpus p2pCorpus) throws  Exception {
        return p2pCorpusMapper.deleteByPrimaryKey(p2pCorpus.getId());
    }

    /**
    * 删除点对点语料
    * @param p2pCorpusId
    * @return
    * @throws Exception
    */
    public int delete(long p2pCorpusId) throws  Exception {
        return p2pCorpusMapper.deleteByPrimaryKey(p2pCorpusId);
    }

    /**
    * 查找所有点对点语料
    * @return
    */
    public List<P2pCorpus> getP2pCorpusAll()  throws  Exception {
        return p2pCorpusMapper.selectByExample(new P2pCorpus_Example());
    }

    /**
    * 通过点对点语料ID查询点对点语料
    * @param p2pCorpusId
    * @return
    * @throws Exception
    */
    public P2pCorpus getP2pCorpusByPK(long p2pCorpusId)  throws  Exception {
        return p2pCorpusMapper.selectByPrimaryKey(p2pCorpusId);
    }


    /**
    * 通过MAP参数查询点对点语料
    * @param params
    * @return
    * @throws Exception
    */
    public List<P2pCorpus> getP2pCorpusListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询点对点语料
    * @param example
    * @return
    * @throws Exception
    */
    public List<P2pCorpus> getP2pCorpusListByExample(P2pCorpus_Example example) throws  Exception {
        return p2pCorpusMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询点对点语料数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getP2pCorpusCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询点对点语料数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getP2pCorpusCountByExample(P2pCorpus_Example example) throws  Exception {
        return p2pCorpusMapper.countByExample(example);
    }


  	//getter
 	
	public P2pCorpusMapper getP2pCorpusMapper(){
		return p2pCorpusMapper;
	}
	//setter
	public void setP2pCorpusMapper(P2pCorpusMapper p2pCorpusMapper){
    	this.p2pCorpusMapper = p2pCorpusMapper;
    }
}
