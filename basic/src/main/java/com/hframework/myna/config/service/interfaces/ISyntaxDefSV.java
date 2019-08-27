package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.SyntaxDef;
import com.hframework.myna.config.domain.model.SyntaxDef_Example;


public interface ISyntaxDefSV   {

  
    /**
    * 创建句法定义
    * @param syntaxDef
    * @return
    * @throws Exception
    */
    public int create(SyntaxDef syntaxDef) throws  Exception;

    /**
    * 批量维护句法定义
    * @param syntaxDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(SyntaxDef[] syntaxDefs) throws  Exception;
    /**
    * 更新句法定义
    * @param syntaxDef
    * @return
    * @throws Exception
    */
    public int update(SyntaxDef syntaxDef) throws  Exception;

    /**
    * 通过查询对象更新句法定义
    * @param syntaxDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(SyntaxDef syntaxDef, SyntaxDef_Example example) throws  Exception;

    /**
    * 删除句法定义
    * @param syntaxDef
    * @return
    * @throws Exception
    */
    public int delete(SyntaxDef syntaxDef) throws  Exception;


    /**
    * 删除句法定义
    * @param syntaxDefId
    * @return
    * @throws Exception
    */
    public int delete(long syntaxDefId) throws  Exception;


    /**
    * 查找所有句法定义
    * @return
    */
    public List<SyntaxDef> getSyntaxDefAll()  throws  Exception;

    /**
    * 通过句法定义ID查询句法定义
    * @param syntaxDefId
    * @return
    * @throws Exception
    */
    public SyntaxDef getSyntaxDefByPK(long syntaxDefId)  throws  Exception;

    /**
    * 通过MAP参数查询句法定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<SyntaxDef> getSyntaxDefListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询句法定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<SyntaxDef> getSyntaxDefListByExample(SyntaxDef_Example example) throws  Exception;


    /**
    * 通过MAP参数查询句法定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getSyntaxDefCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询句法定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getSyntaxDefCountByExample(SyntaxDef_Example example) throws  Exception;


 }
