package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Model;
import com.hframework.myna.config.domain.model.Model_Example;


public interface IModelSV   {

  
    /**
    * 创建工作模式
    * @param model
    * @return
    * @throws Exception
    */
    public int create(Model model) throws  Exception;

    /**
    * 批量维护工作模式
    * @param models
    * @return
    * @throws Exception
    */
    public int batchOperate(Model[] models) throws  Exception;
    /**
    * 更新工作模式
    * @param model
    * @return
    * @throws Exception
    */
    public int update(Model model) throws  Exception;

    /**
    * 通过查询对象更新工作模式
    * @param model
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Model model, Model_Example example) throws  Exception;

    /**
    * 删除工作模式
    * @param model
    * @return
    * @throws Exception
    */
    public int delete(Model model) throws  Exception;


    /**
    * 删除工作模式
    * @param modelId
    * @return
    * @throws Exception
    */
    public int delete(long modelId) throws  Exception;


    /**
    * 查找所有工作模式
    * @return
    */
    public List<Model> getModelAll()  throws  Exception;

    /**
    * 通过工作模式ID查询工作模式
    * @param modelId
    * @return
    * @throws Exception
    */
    public Model getModelByPK(long modelId)  throws  Exception;

    /**
    * 通过MAP参数查询工作模式
    * @param params
    * @return
    * @throws Exception
    */
    public List<Model> getModelListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询工作模式
    * @param example
    * @return
    * @throws Exception
    */
    public List<Model> getModelListByExample(Model_Example example) throws  Exception;


    /**
    * 通过MAP参数查询工作模式数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getModelCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询工作模式数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getModelCountByExample(Model_Example example) throws  Exception;


 }
