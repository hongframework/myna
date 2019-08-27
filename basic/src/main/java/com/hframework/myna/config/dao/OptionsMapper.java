package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Options;
import com.hframework.myna.config.domain.model.Options_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionsMapper {
    int countByExample(Options_Example example);

    int deleteByExample(Options_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Options record);

    int insertSelective(Options record);

    List<Options> selectByExample(Options_Example example);

    Options selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Options record, @Param("example") Options_Example example);

    int updateByExample(@Param("record") Options record, @Param("example") Options_Example example);

    int updateByPrimaryKeySelective(Options record);

    int updateByPrimaryKey(Options record);
}