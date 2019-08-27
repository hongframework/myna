package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Utterance;
import com.hframework.myna.config.domain.model.Utterance_Example;


public interface IUtteranceSV   {

  
    /**
    * 创建话术
    * @param utterance
    * @return
    * @throws Exception
    */
    public int create(Utterance utterance) throws  Exception;

    /**
    * 批量维护话术
    * @param utterances
    * @return
    * @throws Exception
    */
    public int batchOperate(Utterance[] utterances) throws  Exception;
    /**
    * 更新话术
    * @param utterance
    * @return
    * @throws Exception
    */
    public int update(Utterance utterance) throws  Exception;

    /**
    * 通过查询对象更新话术
    * @param utterance
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Utterance utterance, Utterance_Example example) throws  Exception;

    /**
    * 删除话术
    * @param utterance
    * @return
    * @throws Exception
    */
    public int delete(Utterance utterance) throws  Exception;


    /**
    * 删除话术
    * @param utteranceId
    * @return
    * @throws Exception
    */
    public int delete(long utteranceId) throws  Exception;


    /**
    * 查找所有话术
    * @return
    */
    public List<Utterance> getUtteranceAll()  throws  Exception;

    /**
    * 通过话术ID查询话术
    * @param utteranceId
    * @return
    * @throws Exception
    */
    public Utterance getUtteranceByPK(long utteranceId)  throws  Exception;

    /**
    * 通过MAP参数查询话术
    * @param params
    * @return
    * @throws Exception
    */
    public List<Utterance> getUtteranceListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询话术
    * @param example
    * @return
    * @throws Exception
    */
    public List<Utterance> getUtteranceListByExample(Utterance_Example example) throws  Exception;


    /**
    * 通过MAP参数查询话术数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getUtteranceCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询话术数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getUtteranceCountByExample(Utterance_Example example) throws  Exception;


 }
