package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Task;
import com.hframework.myna.config.domain.model.Task_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMapper {
    int countByExample(Task_Example example);

    int deleteByExample(Task_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Task record);

    int insertSelective(Task record);

    List<Task> selectByExample(Task_Example example);

    Task selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Task record, @Param("example") Task_Example example);

    int updateByExample(@Param("record") Task record, @Param("example") Task_Example example);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}