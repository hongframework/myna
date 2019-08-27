package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.QnaActions;
import com.hframework.myna.config.domain.model.QnaActions_Example;
import com.hframework.myna.config.dao.QnaActionsMapper;
import com.hframework.myna.config.service.interfaces.IQnaActionsSV;

@Service("iQnaActionsSV")
public class QnaActionsSVImpl  implements IQnaActionsSV {

	@Resource
	private QnaActionsMapper qnaActionsMapper;
  


    /**
    * 创建问答行为
    * @param qnaActions
    * @return
    * @throws Exception
    */
    public int create(QnaActions qnaActions) throws Exception {
        return qnaActionsMapper.insertSelective(qnaActions);
    }

    /**
    * 批量维护问答行为
    * @param qnaActionss
    * @return
    * @throws Exception
    */
    public int batchOperate(QnaActions[] qnaActionss) throws  Exception{
        int result = 0;
        if(qnaActionss != null) {
            for (QnaActions qnaActions : qnaActionss) {
                if(qnaActions.getId() == null) {
                    result += this.create(qnaActions);
                }else {
                    result += this.update(qnaActions);
                }
            }
        }
        return result;
    }

    /**
    * 更新问答行为
    * @param qnaActions
    * @return
    * @throws Exception
    */
    public int update(QnaActions qnaActions) throws  Exception {
        return qnaActionsMapper.updateByPrimaryKeySelective(qnaActions);
    }

    /**
    * 通过查询对象更新问答行为
    * @param qnaActions
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(QnaActions qnaActions, QnaActions_Example example) throws  Exception {
        return qnaActionsMapper.updateByExampleSelective(qnaActions, example);
    }

    /**
    * 删除问答行为
    * @param qnaActions
    * @return
    * @throws Exception
    */
    public int delete(QnaActions qnaActions) throws  Exception {
        return qnaActionsMapper.deleteByPrimaryKey(qnaActions.getId());
    }

    /**
    * 删除问答行为
    * @param qnaActionsId
    * @return
    * @throws Exception
    */
    public int delete(long qnaActionsId) throws  Exception {
        return qnaActionsMapper.deleteByPrimaryKey(qnaActionsId);
    }

    /**
    * 查找所有问答行为
    * @return
    */
    public List<QnaActions> getQnaActionsAll()  throws  Exception {
        return qnaActionsMapper.selectByExample(new QnaActions_Example());
    }

    /**
    * 通过问答行为ID查询问答行为
    * @param qnaActionsId
    * @return
    * @throws Exception
    */
    public QnaActions getQnaActionsByPK(long qnaActionsId)  throws  Exception {
        return qnaActionsMapper.selectByPrimaryKey(qnaActionsId);
    }


    /**
    * 通过MAP参数查询问答行为
    * @param params
    * @return
    * @throws Exception
    */
    public List<QnaActions> getQnaActionsListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询问答行为
    * @param example
    * @return
    * @throws Exception
    */
    public List<QnaActions> getQnaActionsListByExample(QnaActions_Example example) throws  Exception {
        return qnaActionsMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询问答行为数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getQnaActionsCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询问答行为数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getQnaActionsCountByExample(QnaActions_Example example) throws  Exception {
        return qnaActionsMapper.countByExample(example);
    }


  	//getter
 	
	public QnaActionsMapper getQnaActionsMapper(){
		return qnaActionsMapper;
	}
	//setter
	public void setQnaActionsMapper(QnaActionsMapper qnaActionsMapper){
    	this.qnaActionsMapper = qnaActionsMapper;
    }
}
