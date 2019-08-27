package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.QnaActions;
import com.hframework.myna.config.domain.model.QnaActions_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaActionsMapper {
    int countByExample(QnaActions_Example example);

    int deleteByExample(QnaActions_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(QnaActions record);

    int insertSelective(QnaActions record);

    List<QnaActions> selectByExample(QnaActions_Example example);

    QnaActions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") QnaActions record, @Param("example") QnaActions_Example example);

    int updateByExample(@Param("record") QnaActions record, @Param("example") QnaActions_Example example);

    int updateByPrimaryKeySelective(QnaActions record);

    int updateByPrimaryKey(QnaActions record);
}