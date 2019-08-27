package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.ModelActions;
import com.hframework.myna.config.domain.model.ModelActions_Example;


public interface IModelActionsSV   {

  
    /**
    * 创建模式关联行为
    * @param modelActions
    * @return
    * @throws Exception
    */
    public int create(ModelActions modelActions) throws  Exception;

    /**
    * 批量维护模式关联行为
    * @param modelActionss
    * @return
    * @throws Exception
    */
    public int batchOperate(ModelActions[] modelActionss) throws  Exception;
    /**
    * 更新模式关联行为
    * @param modelActions
    * @return
    * @throws Exception
    */
    public int update(ModelActions modelActions) throws  Exception;

    /**
    * 通过查询对象更新模式关联行为
    * @param modelActions
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(ModelActions modelActions, ModelActions_Example example) throws  Exception;

    /**
    * 删除模式关联行为
    * @param modelActions
    * @return
    * @throws Exception
    */
    public int delete(ModelActions modelActions) throws  Exception;


    /**
    * 删除模式关联行为
    * @param modelActionsId
    * @return
    * @throws Exception
    */
    public int delete(long modelActionsId) throws  Exception;


    /**
    * 查找所有模式关联行为
    * @return
    */
    public List<ModelActions> getModelActionsAll()  throws  Exception;

    /**
    * 通过模式关联行为ID查询模式关联行为
    * @param modelActionsId
    * @return
    * @throws Exception
    */
    public ModelActions getModelActionsByPK(long modelActionsId)  throws  Exception;

    /**
    * 通过MAP参数查询模式关联行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<ModelActions> getModelActionsListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询模式关联行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<ModelActions> getModelActionsListByExample(ModelActions_Example example) throws  Exception;


    /**
    * 通过MAP参数查询模式关联行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getModelActionsCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询模式关联行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getModelActionsCountByExample(ModelActions_Example example) throws  Exception;


 }
