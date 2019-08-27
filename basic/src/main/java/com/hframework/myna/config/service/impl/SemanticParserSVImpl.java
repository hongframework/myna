package com.hframework.myna.config.service.impl;

import java.util.*;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.google.common.collect.Lists;
import com.hframework.common.util.ExampleUtils;
import com.hframework.myna.config.domain.model.SemanticParser;
import com.hframework.myna.config.domain.model.SemanticParser_Example;
import com.hframework.myna.config.dao.SemanticParserMapper;
import com.hframework.myna.config.service.interfaces.ISemanticParserSV;

@Service("iSemanticParserSV")
public class SemanticParserSVImpl  implements ISemanticParserSV {

	@Resource
	private SemanticParserMapper semanticParserMapper;
  


    /**
    * 创建语义解析器
    * @param semanticParser
    * @return
    * @throws Exception
    */
    public int create(SemanticParser semanticParser) throws Exception {
        return semanticParserMapper.insertSelective(semanticParser);
    }

    /**
    * 批量维护语义解析器
    * @param semanticParsers
    * @return
    * @throws Exception
    */
    public int batchOperate(SemanticParser[] semanticParsers) throws  Exception{
        int result = 0;
        if(semanticParsers != null) {
            for (SemanticParser semanticParser : semanticParsers) {
                if(semanticParser.getId() == null) {
                    result += this.create(semanticParser);
                }else {
                    result += this.update(semanticParser);
                }
            }
        }
        return result;
    }

    /**
    * 更新语义解析器
    * @param semanticParser
    * @return
    * @throws Exception
    */
    public int update(SemanticParser semanticParser) throws  Exception {
        return semanticParserMapper.updateByPrimaryKeySelective(semanticParser);
    }

    /**
    * 通过查询对象更新语义解析器
    * @param semanticParser
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(SemanticParser semanticParser, SemanticParser_Example example) throws  Exception {
        return semanticParserMapper.updateByExampleSelective(semanticParser, example);
    }

    /**
    * 删除语义解析器
    * @param semanticParser
    * @return
    * @throws Exception
    */
    public int delete(SemanticParser semanticParser) throws  Exception {
        return semanticParserMapper.deleteByPrimaryKey(semanticParser.getId());
    }

    /**
    * 删除语义解析器
    * @param semanticParserId
    * @return
    * @throws Exception
    */
    public int delete(long semanticParserId) throws  Exception {
        return semanticParserMapper.deleteByPrimaryKey(semanticParserId);
    }

    /**
    * 查找所有语义解析器
    * @return
    */
    public List<SemanticParser> getSemanticParserAll()  throws  Exception {
        return semanticParserMapper.selectByExample(new SemanticParser_Example());
    }

    /**
    * 通过语义解析器ID查询语义解析器
    * @param semanticParserId
    * @return
    * @throws Exception
    */
    public SemanticParser getSemanticParserByPK(long semanticParserId)  throws  Exception {
        return semanticParserMapper.selectByPrimaryKey(semanticParserId);
    }


    /**
    * 通过MAP参数查询语义解析器
    * @param params
    * @return
    * @throws Exception
    */
    public List<SemanticParser> getSemanticParserListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询语义解析器
    * @param example
    * @return
    * @throws Exception
    */
    public List<SemanticParser> getSemanticParserListByExample(SemanticParser_Example example) throws  Exception {
        return semanticParserMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询语义解析器数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getSemanticParserCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询语义解析器数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getSemanticParserCountByExample(SemanticParser_Example example) throws  Exception {
        return semanticParserMapper.countByExample(example);
    }


  	//getter
 	
	public SemanticParserMapper getSemanticParserMapper(){
		return semanticParserMapper;
	}
	//setter
	public void setSemanticParserMapper(SemanticParserMapper semanticParserMapper){
    	this.semanticParserMapper = semanticParserMapper;
    }
}
