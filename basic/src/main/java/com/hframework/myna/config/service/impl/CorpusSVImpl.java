package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Corpus;
import com.hframework.myna.config.domain.model.Corpus_Example;
import com.hframework.myna.config.dao.CorpusMapper;
import com.hframework.myna.config.service.interfaces.ICorpusSV;

@Service("iCorpusSV")
public class CorpusSVImpl  implements ICorpusSV {

	@Resource
	private CorpusMapper corpusMapper;
  


    /**
    * 创建语料库
    * @param corpus
    * @return
    * @throws Exception
    */
    public int create(Corpus corpus) throws Exception {
        return corpusMapper.insertSelective(corpus);
    }

    /**
    * 批量维护语料库
    * @param corpuss
    * @return
    * @throws Exception
    */
    public int batchOperate(Corpus[] corpuss) throws  Exception{
        int result = 0;
        if(corpuss != null) {
            for (Corpus corpus : corpuss) {
                if(corpus.getId() == null) {
                    result += this.create(corpus);
                }else {
                    result += this.update(corpus);
                }
            }
        }
        return result;
    }

    /**
    * 更新语料库
    * @param corpus
    * @return
    * @throws Exception
    */
    public int update(Corpus corpus) throws  Exception {
        return corpusMapper.updateByPrimaryKeySelective(corpus);
    }

    /**
    * 通过查询对象更新语料库
    * @param corpus
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Corpus corpus, Corpus_Example example) throws  Exception {
        return corpusMapper.updateByExampleSelective(corpus, example);
    }

    /**
    * 删除语料库
    * @param corpus
    * @return
    * @throws Exception
    */
    public int delete(Corpus corpus) throws  Exception {
        return corpusMapper.deleteByPrimaryKey(corpus.getId());
    }

    /**
    * 删除语料库
    * @param corpusId
    * @return
    * @throws Exception
    */
    public int delete(long corpusId) throws  Exception {
        return corpusMapper.deleteByPrimaryKey(corpusId);
    }

    /**
    * 查找所有语料库
    * @return
    */
    public List<Corpus> getCorpusAll()  throws  Exception {
        return corpusMapper.selectByExample(new Corpus_Example());
    }

    /**
    * 通过语料库ID查询语料库
    * @param corpusId
    * @return
    * @throws Exception
    */
    public Corpus getCorpusByPK(long corpusId)  throws  Exception {
        return corpusMapper.selectByPrimaryKey(corpusId);
    }


    /**
    * 通过MAP参数查询语料库
    * @param params
    * @return
    * @throws Exception
    */
    public List<Corpus> getCorpusListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询语料库
    * @param example
    * @return
    * @throws Exception
    */
    public List<Corpus> getCorpusListByExample(Corpus_Example example) throws  Exception {
        return corpusMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询语料库数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCorpusCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询语料库数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCorpusCountByExample(Corpus_Example example) throws  Exception {
        return corpusMapper.countByExample(example);
    }


  	//getter
 	
	public CorpusMapper getCorpusMapper(){
		return corpusMapper;
	}
	//setter
	public void setCorpusMapper(CorpusMapper corpusMapper){
    	this.corpusMapper = corpusMapper;
    }
}
