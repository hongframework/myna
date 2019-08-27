package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.QnaActions;
import com.hframework.myna.config.domain.model.QnaActions_Example;


public interface IQnaActionsSV   {

  
    /**
    * 创建问答行为
    * @param qnaActions
    * @return
    * @throws Exception
    */
    public int create(QnaActions qnaActions) throws  Exception;

    /**
    * 批量维护问答行为
    * @param qnaActionss
    * @return
    * @throws Exception
    */
    public int batchOperate(QnaActions[] qnaActionss) throws  Exception;
    /**
    * 更新问答行为
    * @param qnaActions
    * @return
    * @throws Exception
    */
    public int update(QnaActions qnaActions) throws  Exception;

    /**
    * 通过查询对象更新问答行为
    * @param qnaActions
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(QnaActions qnaActions, QnaActions_Example example) throws  Exception;

    /**
    * 删除问答行为
    * @param qnaActions
    * @return
    * @throws Exception
    */
    public int delete(QnaActions qnaActions) throws  Exception;


    /**
    * 删除问答行为
    * @param qnaActionsId
    * @return
    * @throws Exception
    */
    public int delete(long qnaActionsId) throws  Exception;


    /**
    * 查找所有问答行为
    * @return
    */
    public List<QnaActions> getQnaActionsAll()  throws  Exception;

    /**
    * 通过问答行为ID查询问答行为
    * @param qnaActionsId
    * @return
    * @throws Exception
    */
    public QnaActions getQnaActionsByPK(long qnaActionsId)  throws  Exception;

    /**
    * 通过MAP参数查询问答行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<QnaActions> getQnaActionsListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询问答行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<QnaActions> getQnaActionsListByExample(QnaActions_Example example) throws  Exception;


    /**
    * 通过MAP参数查询问答行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getQnaActionsCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询问答行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getQnaActionsCountByExample(QnaActions_Example example) throws  Exception;


 }
