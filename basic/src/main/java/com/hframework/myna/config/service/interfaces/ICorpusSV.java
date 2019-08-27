package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Corpus;
import com.hframework.myna.config.domain.model.Corpus_Example;


public interface ICorpusSV   {

  
    /**
    * 创建语料库
    * @param corpus
    * @return
    * @throws Exception
    */
    public int create(Corpus corpus) throws  Exception;

    /**
    * 批量维护语料库
    * @param corpuss
    * @return
    * @throws Exception
    */
    public int batchOperate(Corpus[] corpuss) throws  Exception;
    /**
    * 更新语料库
    * @param corpus
    * @return
    * @throws Exception
    */
    public int update(Corpus corpus) throws  Exception;

    /**
    * 通过查询对象更新语料库
    * @param corpus
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Corpus corpus, Corpus_Example example) throws  Exception;

    /**
    * 删除语料库
    * @param corpus
    * @return
    * @throws Exception
    */
    public int delete(Corpus corpus) throws  Exception;


    /**
    * 删除语料库
    * @param corpusId
    * @return
    * @throws Exception
    */
    public int delete(long corpusId) throws  Exception;


    /**
    * 查找所有语料库
    * @return
    */
    public List<Corpus> getCorpusAll()  throws  Exception;

    /**
    * 通过语料库ID查询语料库
    * @param corpusId
    * @return
    * @throws Exception
    */
    public Corpus getCorpusByPK(long corpusId)  throws  Exception;

    /**
    * 通过MAP参数查询语料库
    * @param params
    * @return
    * @throws Exception
    */
    public List<Corpus> getCorpusListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询语料库
    * @param example
    * @return
    * @throws Exception
    */
    public List<Corpus> getCorpusListByExample(Corpus_Example example) throws  Exception;


    /**
    * 通过MAP参数查询语料库数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCorpusCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询语料库数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCorpusCountByExample(Corpus_Example example) throws  Exception;


 }
