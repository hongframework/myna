package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Words;
import com.hframework.myna.config.domain.model.Words_Example;
import com.hframework.myna.config.dao.WordsMapper;
import com.hframework.myna.config.service.interfaces.IWordsSV;

@Service("iWordsSV")
public class WordsSVImpl  implements IWordsSV {

	@Resource
	private WordsMapper wordsMapper;
  


    /**
    * 创建词汇
    * @param words
    * @return
    * @throws Exception
    */
    public int create(Words words) throws Exception {
        return wordsMapper.insertSelective(words);
    }

    /**
    * 批量维护词汇
    * @param wordss
    * @return
    * @throws Exception
    */
    public int batchOperate(Words[] wordss) throws  Exception{
        int result = 0;
        if(wordss != null) {
            for (Words words : wordss) {
                if(words.getId() == null) {
                    result += this.create(words);
                }else {
                    result += this.update(words);
                }
            }
        }
        return result;
    }

    /**
    * 更新词汇
    * @param words
    * @return
    * @throws Exception
    */
    public int update(Words words) throws  Exception {
        return wordsMapper.updateByPrimaryKeySelective(words);
    }

    /**
    * 通过查询对象更新词汇
    * @param words
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Words words, Words_Example example) throws  Exception {
        return wordsMapper.updateByExampleSelective(words, example);
    }

    /**
    * 删除词汇
    * @param words
    * @return
    * @throws Exception
    */
    public int delete(Words words) throws  Exception {
        return wordsMapper.deleteByPrimaryKey(words.getId());
    }

    /**
    * 删除词汇
    * @param wordsId
    * @return
    * @throws Exception
    */
    public int delete(long wordsId) throws  Exception {
        return wordsMapper.deleteByPrimaryKey(wordsId);
    }

    /**
    * 查找所有词汇
    * @return
    */
    public List<Words> getWordsAll()  throws  Exception {
        return wordsMapper.selectByExample(new Words_Example());
    }

    /**
    * 通过词汇ID查询词汇
    * @param wordsId
    * @return
    * @throws Exception
    */
    public Words getWordsByPK(long wordsId)  throws  Exception {
        return wordsMapper.selectByPrimaryKey(wordsId);
    }


    /**
    * 通过MAP参数查询词汇
    * @param params
    * @return
    * @throws Exception
    */
    public List<Words> getWordsListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询词汇
    * @param example
    * @return
    * @throws Exception
    */
    public List<Words> getWordsListByExample(Words_Example example) throws  Exception {
        return wordsMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询词汇数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getWordsCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询词汇数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getWordsCountByExample(Words_Example example) throws  Exception {
        return wordsMapper.countByExample(example);
    }


  	//getter
 	
	public WordsMapper getWordsMapper(){
		return wordsMapper;
	}
	//setter
	public void setWordsMapper(WordsMapper wordsMapper){
    	this.wordsMapper = wordsMapper;
    }
}
