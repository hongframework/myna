package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Robot;
import com.hframework.myna.config.domain.model.Robot_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotMapper {
    int countByExample(Robot_Example example);

    int deleteByExample(Robot_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Robot record);

    int insertSelective(Robot record);

    List<Robot> selectByExample(Robot_Example example);

    Robot selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Robot record, @Param("example") Robot_Example example);

    int updateByExample(@Param("record") Robot record, @Param("example") Robot_Example example);

    int updateByPrimaryKeySelective(Robot record);

    int updateByPrimaryKey(Robot record);
}