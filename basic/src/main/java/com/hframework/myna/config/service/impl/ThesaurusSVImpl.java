package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.Thesaurus;
import com.hframework.myna.config.domain.model.Thesaurus_Example;
import com.hframework.myna.config.dao.ThesaurusMapper;
import com.hframework.myna.config.service.interfaces.IThesaurusSV;

@Service("iThesaurusSV")
public class ThesaurusSVImpl  implements IThesaurusSV {

	@Resource
	private ThesaurusMapper thesaurusMapper;
  


    /**
    * 创建词库
    * @param thesaurus
    * @return
    * @throws Exception
    */
    public int create(Thesaurus thesaurus) throws Exception {
        return thesaurusMapper.insertSelective(thesaurus);
    }

    /**
    * 批量维护词库
    * @param thesauruss
    * @return
    * @throws Exception
    */
    public int batchOperate(Thesaurus[] thesauruss) throws  Exception{
        int result = 0;
        if(thesauruss != null) {
            for (Thesaurus thesaurus : thesauruss) {
                if(thesaurus.getId() == null) {
                    result += this.create(thesaurus);
                }else {
                    result += this.update(thesaurus);
                }
            }
        }
        return result;
    }

    /**
    * 更新词库
    * @param thesaurus
    * @return
    * @throws Exception
    */
    public int update(Thesaurus thesaurus) throws  Exception {
        return thesaurusMapper.updateByPrimaryKeySelective(thesaurus);
    }

    /**
    * 通过查询对象更新词库
    * @param thesaurus
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Thesaurus thesaurus, Thesaurus_Example example) throws  Exception {
        return thesaurusMapper.updateByExampleSelective(thesaurus, example);
    }

    /**
    * 删除词库
    * @param thesaurus
    * @return
    * @throws Exception
    */
    public int delete(Thesaurus thesaurus) throws  Exception {
        return thesaurusMapper.deleteByPrimaryKey(thesaurus.getId());
    }

    /**
    * 删除词库
    * @param thesaurusId
    * @return
    * @throws Exception
    */
    public int delete(long thesaurusId) throws  Exception {
        return thesaurusMapper.deleteByPrimaryKey(thesaurusId);
    }

    /**
    * 查找所有词库
    * @return
    */
    public List<Thesaurus> getThesaurusAll()  throws  Exception {
        return thesaurusMapper.selectByExample(new Thesaurus_Example());
    }

    /**
    * 通过词库ID查询词库
    * @param thesaurusId
    * @return
    * @throws Exception
    */
    public Thesaurus getThesaurusByPK(long thesaurusId)  throws  Exception {
        return thesaurusMapper.selectByPrimaryKey(thesaurusId);
    }


    /**
    * 通过MAP参数查询词库
    * @param params
    * @return
    * @throws Exception
    */
    public List<Thesaurus> getThesaurusListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询词库
    * @param example
    * @return
    * @throws Exception
    */
    public List<Thesaurus> getThesaurusListByExample(Thesaurus_Example example) throws  Exception {
        return thesaurusMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询词库数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getThesaurusCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询词库数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getThesaurusCountByExample(Thesaurus_Example example) throws  Exception {
        return thesaurusMapper.countByExample(example);
    }


  	//getter
 	
	public ThesaurusMapper getThesaurusMapper(){
		return thesaurusMapper;
	}
	//setter
	public void setThesaurusMapper(ThesaurusMapper thesaurusMapper){
    	this.thesaurusMapper = thesaurusMapper;
    }
}
