package com.hframework.myna.config.service.interfaces;

import java.util.*;
import com.hframework.myna.config.domain.model.P2pReply;
import com.hframework.myna.config.domain.model.P2pReply_Example;


public interface IP2pReplySV   {

  
    /**
    * 创建点对点应答
    * @param p2pReply
    * @return
    * @throws Exception
    */
    public int create(P2pReply p2pReply) throws  Exception;

    /**
    * 批量维护点对点应答
    * @param p2pReplys
    * @return
    * @throws Exception
    */
    public int batchOperate(P2pReply[] p2pReplys) throws  Exception;
    /**
    * 更新点对点应答
    * @param p2pReply
    * @return
    * @throws Exception
    */
    public int update(P2pReply p2pReply) throws  Exception;

    /**
    * 通过查询对象更新点对点应答
    * @param p2pReply
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(P2pReply p2pReply, P2pReply_Example example) throws  Exception;

    /**
    * 删除点对点应答
    * @param p2pReply
    * @return
    * @throws Exception
    */
    public int delete(P2pReply p2pReply) throws  Exception;


    /**
    * 删除点对点应答
    * @param p2pReplyId
    * @return
    * @throws Exception
    */
    public int delete(long p2pReplyId) throws  Exception;


    /**
    * 查找所有点对点应答
    * @return
    */
    public List<P2pReply> getP2pReplyAll()  throws  Exception;

    /**
    * 通过点对点应答ID查询点对点应答
    * @param p2pReplyId
    * @return
    * @throws Exception
    */
    public P2pReply getP2pReplyByPK(long p2pReplyId)  throws  Exception;

    /**
    * 通过MAP参数查询点对点应答
    * @param params
    * @return
    * @throws Exception
    */
    public List<P2pReply> getP2pReplyListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询点对点应答
    * @param example
    * @return
    * @throws Exception
    */
    public List<P2pReply> getP2pReplyListByExample(P2pReply_Example example) throws  Exception;


    /**
    * 通过MAP参数查询点对点应答数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getP2pReplyCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询点对点应答数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getP2pReplyCountByExample(P2pReply_Example example) throws  Exception;


 }
