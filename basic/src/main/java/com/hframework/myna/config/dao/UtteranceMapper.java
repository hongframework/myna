package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Utterance;
import com.hframework.myna.config.domain.model.Utterance_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtteranceMapper {
    int countByExample(Utterance_Example example);

    int deleteByExample(Utterance_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Utterance record);

    int insertSelective(Utterance record);

    List<Utterance> selectByExample(Utterance_Example example);

    Utterance selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Utterance record, @Param("example") Utterance_Example example);

    int updateByExample(@Param("record") Utterance record, @Param("example") Utterance_Example example);

    int updateByPrimaryKeySelective(Utterance record);

    int updateByPrimaryKey(Utterance record);
}