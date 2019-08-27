package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.SemanticParser;
import com.hframework.myna.config.domain.model.SemanticParser_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SemanticParserMapper {
    int countByExample(SemanticParser_Example example);

    int deleteByExample(SemanticParser_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(SemanticParser record);

    int insertSelective(SemanticParser record);

    List<SemanticParser> selectByExample(SemanticParser_Example example);

    SemanticParser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SemanticParser record, @Param("example") SemanticParser_Example example);

    int updateByExample(@Param("record") SemanticParser record, @Param("example") SemanticParser_Example example);

    int updateByPrimaryKeySelective(SemanticParser record);

    int updateByPrimaryKey(SemanticParser record);
}