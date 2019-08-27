package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Thesaurus;
import com.hframework.myna.config.domain.model.Thesaurus_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThesaurusMapper {
    int countByExample(Thesaurus_Example example);

    int deleteByExample(Thesaurus_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Thesaurus record);

    int insertSelective(Thesaurus record);

    List<Thesaurus> selectByExample(Thesaurus_Example example);

    Thesaurus selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Thesaurus record, @Param("example") Thesaurus_Example example);

    int updateByExample(@Param("record") Thesaurus record, @Param("example") Thesaurus_Example example);

    int updateByPrimaryKeySelective(Thesaurus record);

    int updateByPrimaryKey(Thesaurus record);
}