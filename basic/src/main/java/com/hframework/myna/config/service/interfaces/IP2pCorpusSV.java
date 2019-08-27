package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.P2pCorpus;
import com.hframework.myna.config.domain.model.P2pCorpus_Example;


public interface IP2pCorpusSV   {

  
    /**
    * 创建点对点语料
    * @param p2pCorpus
    * @return
    * @throws Exception
    */
    public int create(P2pCorpus p2pCorpus) throws  Exception;

    /**
    * 批量维护点对点语料
    * @param p2pCorpuss
    * @return
    * @throws Exception
    */
    public int batchOperate(P2pCorpus[] p2pCorpuss) throws  Exception;
    /**
    * 更新点对点语料
    * @param p2pCorpus
    * @return
    * @throws Exception
    */
    public int update(P2pCorpus p2pCorpus) throws  Exception;

    /**
    * 通过查询对象更新点对点语料
    * @param p2pCorpus
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(P2pCorpus p2pCorpus, P2pCorpus_Example example) throws  Exception;

    /**
    * 删除点对点语料
    * @param p2pCorpus
    * @return
    * @throws Exception
    */
    public int delete(P2pCorpus p2pCorpus) throws  Exception;


    /**
    * 删除点对点语料
    * @param p2pCorpusId
    * @return
    * @throws Exception
    */
    public int delete(long p2pCorpusId) throws  Exception;


    /**
    * 查找所有点对点语料
    * @return
    */
    public List<P2pCorpus> getP2pCorpusAll()  throws  Exception;

    /**
    * 通过点对点语料ID查询点对点语料
    * @param p2pCorpusId
    * @return
    * @throws Exception
    */
    public P2pCorpus getP2pCorpusByPK(long p2pCorpusId)  throws  Exception;

    /**
    * 通过MAP参数查询点对点语料
    * @param params
    * @return
    * @throws Exception
    */
    public List<P2pCorpus> getP2pCorpusListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询点对点语料
    * @param example
    * @return
    * @throws Exception
    */
    public List<P2pCorpus> getP2pCorpusListByExample(P2pCorpus_Example example) throws  Exception;


    /**
    * 通过MAP参数查询点对点语料数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getP2pCorpusCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询点对点语料数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getP2pCorpusCountByExample(P2pCorpus_Example example) throws  Exception;


 }
