package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Words;
import com.hframework.myna.config.domain.model.Words_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordsMapper {
    int countByExample(Words_Example example);

    int deleteByExample(Words_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Words record);

    int insertSelective(Words record);

    List<Words> selectByExample(Words_Example example);

    Words selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Words record, @Param("example") Words_Example example);

    int updateByExample(@Param("record") Words record, @Param("example") Words_Example example);

    int updateByPrimaryKeySelective(Words record);

    int updateByPrimaryKey(Words record);
}