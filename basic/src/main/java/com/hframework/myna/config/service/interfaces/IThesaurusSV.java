package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.Thesaurus;
import com.hframework.myna.config.domain.model.Thesaurus_Example;


public interface IThesaurusSV   {

  
    /**
    * 创建词库
    * @param thesaurus
    * @return
    * @throws Exception
    */
    public int create(Thesaurus thesaurus) throws  Exception;

    /**
    * 批量维护词库
    * @param thesauruss
    * @return
    * @throws Exception
    */
    public int batchOperate(Thesaurus[] thesauruss) throws  Exception;
    /**
    * 更新词库
    * @param thesaurus
    * @return
    * @throws Exception
    */
    public int update(Thesaurus thesaurus) throws  Exception;

    /**
    * 通过查询对象更新词库
    * @param thesaurus
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Thesaurus thesaurus, Thesaurus_Example example) throws  Exception;

    /**
    * 删除词库
    * @param thesaurus
    * @return
    * @throws Exception
    */
    public int delete(Thesaurus thesaurus) throws  Exception;


    /**
    * 删除词库
    * @param thesaurusId
    * @return
    * @throws Exception
    */
    public int delete(long thesaurusId) throws  Exception;


    /**
    * 查找所有词库
    * @return
    */
    public List<Thesaurus> getThesaurusAll()  throws  Exception;

    /**
    * 通过词库ID查询词库
    * @param thesaurusId
    * @return
    * @throws Exception
    */
    public Thesaurus getThesaurusByPK(long thesaurusId)  throws  Exception;

    /**
    * 通过MAP参数查询词库
    * @param params
    * @return
    * @throws Exception
    */
    public List<Thesaurus> getThesaurusListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询词库
    * @param example
    * @return
    * @throws Exception
    */
    public List<Thesaurus> getThesaurusListByExample(Thesaurus_Example example) throws  Exception;


    /**
    * 通过MAP参数查询词库数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getThesaurusCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询词库数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getThesaurusCountByExample(Thesaurus_Example example) throws  Exception;


 }
