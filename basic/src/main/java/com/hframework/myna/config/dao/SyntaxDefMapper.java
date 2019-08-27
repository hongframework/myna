package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.SyntaxDef;
import com.hframework.myna.config.domain.model.SyntaxDef_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SyntaxDefMapper {
    int countByExample(SyntaxDef_Example example);

    int deleteByExample(SyntaxDef_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(SyntaxDef record);

    int insertSelective(SyntaxDef record);

    List<SyntaxDef> selectByExample(SyntaxDef_Example example);

    SyntaxDef selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SyntaxDef record, @Param("example") SyntaxDef_Example example);

    int updateByExample(@Param("record") SyntaxDef record, @Param("example") SyntaxDef_Example example);

    int updateByPrimaryKeySelective(SyntaxDef record);

    int updateByPrimaryKey(SyntaxDef record);
}