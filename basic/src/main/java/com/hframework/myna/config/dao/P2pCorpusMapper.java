package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.P2pCorpus;
import com.hframework.myna.config.domain.model.P2pCorpus_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface P2pCorpusMapper {
    int countByExample(P2pCorpus_Example example);

    int deleteByExample(P2pCorpus_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(P2pCorpus record);

    int insertSelective(P2pCorpus record);

    List<P2pCorpus> selectByExample(P2pCorpus_Example example);

    P2pCorpus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") P2pCorpus record, @Param("example") P2pCorpus_Example example);

    int updateByExample(@Param("record") P2pCorpus record, @Param("example") P2pCorpus_Example example);

    int updateByPrimaryKeySelective(P2pCorpus record);

    int updateByPrimaryKey(P2pCorpus record);
}