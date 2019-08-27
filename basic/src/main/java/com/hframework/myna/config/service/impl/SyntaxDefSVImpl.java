package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.SyntaxDef;
import com.hframework.myna.config.domain.model.SyntaxDef_Example;
import com.hframework.myna.config.dao.SyntaxDefMapper;
import com.hframework.myna.config.service.interfaces.ISyntaxDefSV;

@Service("iSyntaxDefSV")
public class SyntaxDefSVImpl  implements ISyntaxDefSV {

	@Resource
	private SyntaxDefMapper syntaxDefMapper;
  


    /**
    * 创建句法定义
    * @param syntaxDef
    * @return
    * @throws Exception
    */
    public int create(SyntaxDef syntaxDef) throws Exception {
        return syntaxDefMapper.insertSelective(syntaxDef);
    }

    /**
    * 批量维护句法定义
    * @param syntaxDefs
    * @return
    * @throws Exception
    */
    public int batchOperate(SyntaxDef[] syntaxDefs) throws  Exception{
        int result = 0;
        if(syntaxDefs != null) {
            for (SyntaxDef syntaxDef : syntaxDefs) {
                if(syntaxDef.getId() == null) {
                    result += this.create(syntaxDef);
                }else {
                    result += this.update(syntaxDef);
                }
            }
        }
        return result;
    }

    /**
    * 更新句法定义
    * @param syntaxDef
    * @return
    * @throws Exception
    */
    public int update(SyntaxDef syntaxDef) throws  Exception {
        return syntaxDefMapper.updateByPrimaryKeySelective(syntaxDef);
    }

    /**
    * 通过查询对象更新句法定义
    * @param syntaxDef
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(SyntaxDef syntaxDef, SyntaxDef_Example example) throws  Exception {
        return syntaxDefMapper.updateByExampleSelective(syntaxDef, example);
    }

    /**
    * 删除句法定义
    * @param syntaxDef
    * @return
    * @throws Exception
    */
    public int delete(SyntaxDef syntaxDef) throws  Exception {
        return syntaxDefMapper.deleteByPrimaryKey(syntaxDef.getId());
    }

    /**
    * 删除句法定义
    * @param syntaxDefId
    * @return
    * @throws Exception
    */
    public int delete(long syntaxDefId) throws  Exception {
        return syntaxDefMapper.deleteByPrimaryKey(syntaxDefId);
    }

    /**
    * 查找所有句法定义
    * @return
    */
    public List<SyntaxDef> getSyntaxDefAll()  throws  Exception {
        return syntaxDefMapper.selectByExample(new SyntaxDef_Example());
    }

    /**
    * 通过句法定义ID查询句法定义
    * @param syntaxDefId
    * @return
    * @throws Exception
    */
    public SyntaxDef getSyntaxDefByPK(long syntaxDefId)  throws  Exception {
        return syntaxDefMapper.selectByPrimaryKey(syntaxDefId);
    }


    /**
    * 通过MAP参数查询句法定义
    * @param params
    * @return
    * @throws Exception
    */
    public List<SyntaxDef> getSyntaxDefListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询句法定义
    * @param example
    * @return
    * @throws Exception
    */
    public List<SyntaxDef> getSyntaxDefListByExample(SyntaxDef_Example example) throws  Exception {
        return syntaxDefMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询句法定义数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getSyntaxDefCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询句法定义数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getSyntaxDefCountByExample(SyntaxDef_Example example) throws  Exception {
        return syntaxDefMapper.countByExample(example);
    }


  	//getter
 	
	public SyntaxDefMapper getSyntaxDefMapper(){
		return syntaxDefMapper;
	}
	//setter
	public void setSyntaxDefMapper(SyntaxDefMapper syntaxDefMapper){
    	this.syntaxDefMapper = syntaxDefMapper;
    }
}
