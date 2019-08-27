package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Options;
import com.hframework.myna.config.domain.model.Options_Example;


public interface IOptionsSV   {

  
    /**
    * 创建选项集
    * @param options
    * @return
    * @throws Exception
    */
    public int create(Options options) throws  Exception;

    /**
    * 批量维护选项集
    * @param optionss
    * @return
    * @throws Exception
    */
    public int batchOperate(Options[] optionss) throws  Exception;
    /**
    * 更新选项集
    * @param options
    * @return
    * @throws Exception
    */
    public int update(Options options) throws  Exception;

    /**
    * 通过查询对象更新选项集
    * @param options
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Options options, Options_Example example) throws  Exception;

    /**
    * 删除选项集
    * @param options
    * @return
    * @throws Exception
    */
    public int delete(Options options) throws  Exception;


    /**
    * 删除选项集
    * @param optionsId
    * @return
    * @throws Exception
    */
    public int delete(long optionsId) throws  Exception;


    /**
    * 查找所有选项集
    * @return
    */
    public List<Options> getOptionsAll()  throws  Exception;

    /**
    * 通过选项集ID查询选项集
    * @param optionsId
    * @return
    * @throws Exception
    */
    public Options getOptionsByPK(long optionsId)  throws  Exception;

    /**
    * 通过MAP参数查询选项集
    * @param params
    * @return
    * @throws Exception
    */
    public List<Options> getOptionsListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询选项集
    * @param example
    * @return
    * @throws Exception
    */
    public List<Options> getOptionsListByExample(Options_Example example) throws  Exception;


    /**
    * 通过MAP参数查询选项集数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getOptionsCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询选项集数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getOptionsCountByExample(Options_Example example) throws  Exception;


 }
