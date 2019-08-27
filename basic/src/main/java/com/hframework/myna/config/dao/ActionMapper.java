package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Action;
import com.hframework.myna.config.domain.model.Action_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionMapper {
    int countByExample(Action_Example example);

    int deleteByExample(Action_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Action record);

    int insertSelective(Action record);

    List<Action> selectByExample(Action_Example example);

    Action selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Action record, @Param("example") Action_Example example);

    int updateByExample(@Param("record") Action record, @Param("example") Action_Example example);

    int updateByPrimaryKeySelective(Action record);

    int updateByPrimaryKey(Action record);
}