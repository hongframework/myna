package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Words;
import com.hframework.myna.config.domain.model.Words_Example;


public interface IWordsSV   {

  
    /**
    * 创建词汇
    * @param words
    * @return
    * @throws Exception
    */
    public int create(Words words) throws  Exception;

    /**
    * 批量维护词汇
    * @param wordss
    * @return
    * @throws Exception
    */
    public int batchOperate(Words[] wordss) throws  Exception;
    /**
    * 更新词汇
    * @param words
    * @return
    * @throws Exception
    */
    public int update(Words words) throws  Exception;

    /**
    * 通过查询对象更新词汇
    * @param words
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Words words, Words_Example example) throws  Exception;

    /**
    * 删除词汇
    * @param words
    * @return
    * @throws Exception
    */
    public int delete(Words words) throws  Exception;


    /**
    * 删除词汇
    * @param wordsId
    * @return
    * @throws Exception
    */
    public int delete(long wordsId) throws  Exception;


    /**
    * 查找所有词汇
    * @return
    */
    public List<Words> getWordsAll()  throws  Exception;

    /**
    * 通过词汇ID查询词汇
    * @param wordsId
    * @return
    * @throws Exception
    */
    public Words getWordsByPK(long wordsId)  throws  Exception;

    /**
    * 通过MAP参数查询词汇
    * @param params
    * @return
    * @throws Exception
    */
    public List<Words> getWordsListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询词汇
    * @param example
    * @return
    * @throws Exception
    */
    public List<Words> getWordsListByExample(Words_Example example) throws  Exception;


    /**
    * 通过MAP参数查询词汇数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getWordsCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询词汇数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getWordsCountByExample(Words_Example example) throws  Exception;


 }
