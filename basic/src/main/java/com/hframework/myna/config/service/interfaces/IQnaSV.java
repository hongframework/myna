package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Qna;
import com.hframework.myna.config.domain.model.Qna_Example;


public interface IQnaSV   {

  
    /**
    * 创建问答
    * @param qna
    * @return
    * @throws Exception
    */
    public int create(Qna qna) throws  Exception;

    /**
    * 批量维护问答
    * @param qnas
    * @return
    * @throws Exception
    */
    public int batchOperate(Qna[] qnas) throws  Exception;
    /**
    * 更新问答
    * @param qna
    * @return
    * @throws Exception
    */
    public int update(Qna qna) throws  Exception;

    /**
    * 通过查询对象更新问答
    * @param qna
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Qna qna, Qna_Example example) throws  Exception;

    /**
    * 删除问答
    * @param qna
    * @return
    * @throws Exception
    */
    public int delete(Qna qna) throws  Exception;


    /**
    * 删除问答
    * @param qnaId
    * @return
    * @throws Exception
    */
    public int delete(long qnaId) throws  Exception;


    /**
    * 查找所有问答
    * @return
    */
    public List<Qna> getQnaAll()  throws  Exception;

    /**
    * 通过问答ID查询问答
    * @param qnaId
    * @return
    * @throws Exception
    */
    public Qna getQnaByPK(long qnaId)  throws  Exception;

    /**
    * 通过MAP参数查询问答
    * @param params
    * @return
    * @throws Exception
    */
    public List<Qna> getQnaListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询问答
    * @param example
    * @return
    * @throws Exception
    */
    public List<Qna> getQnaListByExample(Qna_Example example) throws  Exception;


    /**
    * 通过MAP参数查询问答数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getQnaCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询问答数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getQnaCountByExample(Qna_Example example) throws  Exception;


 }
