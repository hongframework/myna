package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.TaskVersion;
import com.hframework.myna.config.domain.model.TaskVersion_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskVersionMapper {
    int countByExample(TaskVersion_Example example);

    int deleteByExample(TaskVersion_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskVersion record);

    int insertSelective(TaskVersion record);

    List<TaskVersion> selectByExampleWithBLOBs(TaskVersion_Example example);

    List<TaskVersion> selectByExample(TaskVersion_Example example);

    TaskVersion selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaskVersion record, @Param("example") TaskVersion_Example example);

    int updateByExampleWithBLOBs(@Param("record") TaskVersion record, @Param("example") TaskVersion_Example example);

    int updateByExample(@Param("record") TaskVersion record, @Param("example") TaskVersion_Example example);

    int updateByPrimaryKeySelective(TaskVersion record);

    int updateByPrimaryKeyWithBLOBs(TaskVersion record);

    int updateByPrimaryKey(TaskVersion record);
}