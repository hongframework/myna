package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.TaskActions;
import com.hframework.myna.config.domain.model.TaskActions_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskActionsMapper {
    int countByExample(TaskActions_Example example);

    int deleteByExample(TaskActions_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskActions record);

    int insertSelective(TaskActions record);

    List<TaskActions> selectByExample(TaskActions_Example example);

    TaskActions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaskActions record, @Param("example") TaskActions_Example example);

    int updateByExample(@Param("record") TaskActions record, @Param("example") TaskActions_Example example);

    int updateByPrimaryKeySelective(TaskActions record);

    int updateByPrimaryKey(TaskActions record);
}