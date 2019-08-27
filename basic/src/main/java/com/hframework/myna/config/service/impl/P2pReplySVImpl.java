package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.P2pReply;
import com.hframework.myna.config.domain.model.P2pReply_Example;
import com.hframework.myna.config.dao.P2pReplyMapper;
import com.hframework.myna.config.service.interfaces.IP2pReplySV;

@Service("iP2pReplySV")
public class P2pReplySVImpl  implements IP2pReplySV {

	@Resource
	private P2pReplyMapper p2pReplyMapper;
  


    /**
    * 创建点对点应答
    * @param p2pReply
    * @return
    * @throws Exception
    */
    public int create(P2pReply p2pReply) throws Exception {
        return p2pReplyMapper.insertSelective(p2pReply);
    }

    /**
    * 批量维护点对点应答
    * @param p2pReplys
    * @return
    * @throws Exception
    */
    public int batchOperate(P2pReply[] p2pReplys) throws  Exception{
        int result = 0;
        if(p2pReplys != null) {
            for (P2pReply p2pReply : p2pReplys) {
                if(p2pReply.getId() == null) {
                    result += this.create(p2pReply);
                }else {
                    result += this.update(p2pReply);
                }
            }
        }
        return result;
    }

    /**
    * 更新点对点应答
    * @param p2pReply
    * @return
    * @throws Exception
    */
    public int update(P2pReply p2pReply) throws  Exception {
        return p2pReplyMapper.updateByPrimaryKeySelective(p2pReply);
    }

    /**
    * 通过查询对象更新点对点应答
    * @param p2pReply
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(P2pReply p2pReply, P2pReply_Example example) throws  Exception {
        return p2pReplyMapper.updateByExampleSelective(p2pReply, example);
    }

    /**
    * 删除点对点应答
    * @param p2pReply
    * @return
    * @throws Exception
    */
    public int delete(P2pReply p2pReply) throws  Exception {
        return p2pReplyMapper.deleteByPrimaryKey(p2pReply.getId());
    }

    /**
    * 删除点对点应答
    * @param p2pReplyId
    * @return
    * @throws Exception
    */
    public int delete(long p2pReplyId) throws  Exception {
        return p2pReplyMapper.deleteByPrimaryKey(p2pReplyId);
    }

    /**
    * 查找所有点对点应答
    * @return
    */
    public List<P2pReply> getP2pReplyAll()  throws  Exception {
        return p2pReplyMapper.selectByExample(new P2pReply_Example());
    }

    /**
    * 通过点对点应答ID查询点对点应答
    * @param p2pReplyId
    * @return
    * @throws Exception
    */
    public P2pReply getP2pReplyByPK(long p2pReplyId)  throws  Exception {
        return p2pReplyMapper.selectByPrimaryKey(p2pReplyId);
    }


    /**
    * 通过MAP参数查询点对点应答
    * @param params
    * @return
    * @throws Exception
    */
    public List<P2pReply> getP2pReplyListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询点对点应答
    * @param example
    * @return
    * @throws Exception
    */
    public List<P2pReply> getP2pReplyListByExample(P2pReply_Example example) throws  Exception {
        return p2pReplyMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询点对点应答数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getP2pReplyCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询点对点应答数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getP2pReplyCountByExample(P2pReply_Example example) throws  Exception {
        return p2pReplyMapper.countByExample(example);
    }


  	//getter
 	
	public P2pReplyMapper getP2pReplyMapper(){
		return p2pReplyMapper;
	}
	//setter
	public void setP2pReplyMapper(P2pReplyMapper p2pReplyMapper){
    	this.p2pReplyMapper = p2pReplyMapper;
    }
}
