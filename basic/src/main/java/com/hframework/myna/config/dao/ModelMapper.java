package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Model;
import com.hframework.myna.config.domain.model.Model_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelMapper {
    int countByExample(Model_Example example);

    int deleteByExample(Model_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Model record);

    int insertSelective(Model record);

    List<Model> selectByExample(Model_Example example);

    Model selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Model record, @Param("example") Model_Example example);

    int updateByExample(@Param("record") Model record, @Param("example") Model_Example example);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);
}