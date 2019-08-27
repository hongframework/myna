package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.SemanticParser;
import com.hframework.myna.config.domain.model.SemanticParser_Example;


public interface ISemanticParserSV   {

  
    /**
    * 创建语义解析器
    * @param semanticParser
    * @return
    * @throws Exception
    */
    public int create(SemanticParser semanticParser) throws  Exception;

    /**
    * 批量维护语义解析器
    * @param semanticParsers
    * @return
    * @throws Exception
    */
    public int batchOperate(SemanticParser[] semanticParsers) throws  Exception;
    /**
    * 更新语义解析器
    * @param semanticParser
    * @return
    * @throws Exception
    */
    public int update(SemanticParser semanticParser) throws  Exception;

    /**
    * 通过查询对象更新语义解析器
    * @param semanticParser
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(SemanticParser semanticParser, SemanticParser_Example example) throws  Exception;

    /**
    * 删除语义解析器
    * @param semanticParser
    * @return
    * @throws Exception
    */
    public int delete(SemanticParser semanticParser) throws  Exception;


    /**
    * 删除语义解析器
    * @param semanticParserId
    * @return
    * @throws Exception
    */
    public int delete(long semanticParserId) throws  Exception;


    /**
    * 查找所有语义解析器
    * @return
    */
    public List<SemanticParser> getSemanticParserAll()  throws  Exception;

    /**
    * 通过语义解析器ID查询语义解析器
    * @param semanticParserId
    * @return
    * @throws Exception
    */
    public SemanticParser getSemanticParserByPK(long semanticParserId)  throws  Exception;

    /**
    * 通过MAP参数查询语义解析器
    * @param params
    * @return
    * @throws Exception
    */
    public List<SemanticParser> getSemanticParserListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询语义解析器
    * @param example
    * @return
    * @throws Exception
    */
    public List<SemanticParser> getSemanticParserListByExample(SemanticParser_Example example) throws  Exception;


    /**
    * 通过MAP参数查询语义解析器数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getSemanticParserCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询语义解析器数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getSemanticParserCountByExample(SemanticParser_Example example) throws  Exception;


 }
