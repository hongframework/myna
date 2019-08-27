package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Qna;
import com.hframework.myna.config.domain.model.Qna_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaMapper {
    int countByExample(Qna_Example example);

    int deleteByExample(Qna_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Qna record);

    int insertSelective(Qna record);

    List<Qna> selectByExample(Qna_Example example);

    Qna selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Qna record, @Param("example") Qna_Example example);

    int updateByExample(@Param("record") Qna record, @Param("example") Qna_Example example);

    int updateByPrimaryKeySelective(Qna record);

    int updateByPrimaryKey(Qna record);
}