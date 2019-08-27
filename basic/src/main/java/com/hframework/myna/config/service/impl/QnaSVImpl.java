package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Qna;
import com.hframework.myna.config.domain.model.Qna_Example;
import com.hframework.myna.config.dao.QnaMapper;
import com.hframework.myna.config.service.interfaces.IQnaSV;

@Service("iQnaSV")
public class QnaSVImpl  implements IQnaSV {

	@Resource
	private QnaMapper qnaMapper;
  


    /**
    * 创建问答
    * @param qna
    * @return
    * @throws Exception
    */
    public int create(Qna qna) throws Exception {
        return qnaMapper.insertSelective(qna);
    }

    /**
    * 批量维护问答
    * @param qnas
    * @return
    * @throws Exception
    */
    public int batchOperate(Qna[] qnas) throws  Exception{
        int result = 0;
        if(qnas != null) {
            for (Qna qna : qnas) {
                if(qna.getId() == null) {
                    result += this.create(qna);
                }else {
                    result += this.update(qna);
                }
            }
        }
        return result;
    }

    /**
    * 更新问答
    * @param qna
    * @return
    * @throws Exception
    */
    public int update(Qna qna) throws  Exception {
        return qnaMapper.updateByPrimaryKeySelective(qna);
    }

    /**
    * 通过查询对象更新问答
    * @param qna
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Qna qna, Qna_Example example) throws  Exception {
        return qnaMapper.updateByExampleSelective(qna, example);
    }

    /**
    * 删除问答
    * @param qna
    * @return
    * @throws Exception
    */
    public int delete(Qna qna) throws  Exception {
        return qnaMapper.deleteByPrimaryKey(qna.getId());
    }

    /**
    * 删除问答
    * @param qnaId
    * @return
    * @throws Exception
    */
    public int delete(long qnaId) throws  Exception {
        return qnaMapper.deleteByPrimaryKey(qnaId);
    }

    /**
    * 查找所有问答
    * @return
    */
    public List<Qna> getQnaAll()  throws  Exception {
        return qnaMapper.selectByExample(new Qna_Example());
    }

    /**
    * 通过问答ID查询问答
    * @param qnaId
    * @return
    * @throws Exception
    */
    public Qna getQnaByPK(long qnaId)  throws  Exception {
        return qnaMapper.selectByPrimaryKey(qnaId);
    }


    /**
    * 通过MAP参数查询问答
    * @param params
    * @return
    * @throws Exception
    */
    public List<Qna> getQnaListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询问答
    * @param example
    * @return
    * @throws Exception
    */
    public List<Qna> getQnaListByExample(Qna_Example example) throws  Exception {
        return qnaMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询问答数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getQnaCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询问答数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getQnaCountByExample(Qna_Example example) throws  Exception {
        return qnaMapper.countByExample(example);
    }


  	//getter
 	
	public QnaMapper getQnaMapper(){
		return qnaMapper;
	}
	//setter
	public void setQnaMapper(QnaMapper qnaMapper){
    	this.qnaMapper = qnaMapper;
    }
}
