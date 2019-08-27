package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Option;
import com.hframework.myna.config.domain.model.Option_Example;


public interface IOptionSV   {

  
    /**
    * 创建选项
    * @param option
    * @return
    * @throws Exception
    */
    public int create(Option option) throws  Exception;

    /**
    * 批量维护选项
    * @param options
    * @return
    * @throws Exception
    */
    public int batchOperate(Option[] options) throws  Exception;
    /**
    * 更新选项
    * @param option
    * @return
    * @throws Exception
    */
    public int update(Option option) throws  Exception;

    /**
    * 通过查询对象更新选项
    * @param option
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Option option, Option_Example example) throws  Exception;

    /**
    * 删除选项
    * @param option
    * @return
    * @throws Exception
    */
    public int delete(Option option) throws  Exception;


    /**
    * 删除选项
    * @param optionId
    * @return
    * @throws Exception
    */
    public int delete(long optionId) throws  Exception;


    /**
    * 查找所有选项
    * @return
    */
    public List<Option> getOptionAll()  throws  Exception;

    /**
    * 通过选项ID查询选项
    * @param optionId
    * @return
    * @throws Exception
    */
    public Option getOptionByPK(long optionId)  throws  Exception;

    /**
    * 通过MAP参数查询选项
    * @param params
    * @return
    * @throws Exception
    */
    public List<Option> getOptionListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询选项
    * @param example
    * @return
    * @throws Exception
    */
    public List<Option> getOptionListByExample(Option_Example example) throws  Exception;


    /**
    * 通过MAP参数查询选项数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getOptionCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询选项数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getOptionCountByExample(Option_Example example) throws  Exception;


 }
