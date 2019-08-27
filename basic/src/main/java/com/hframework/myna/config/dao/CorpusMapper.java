package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Corpus;
import com.hframework.myna.config.domain.model.Corpus_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpusMapper {
    int countByExample(Corpus_Example example);

    int deleteByExample(Corpus_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Corpus record);

    int insertSelective(Corpus record);

    List<Corpus> selectByExample(Corpus_Example example);

    Corpus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Corpus record, @Param("example") Corpus_Example example);

    int updateByExample(@Param("record") Corpus record, @Param("example") Corpus_Example example);

    int updateByPrimaryKeySelective(Corpus record);

    int updateByPrimaryKey(Corpus record);
}