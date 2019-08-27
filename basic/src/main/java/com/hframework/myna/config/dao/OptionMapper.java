package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.Option;
import com.hframework.myna.config.domain.model.Option_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionMapper {
    int countByExample(Option_Example example);

    int deleteByExample(Option_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(Option record);

    int insertSelective(Option record);

    List<Option> selectByExample(Option_Example example);

    Option selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Option record, @Param("example") Option_Example example);

    int updateByExample(@Param("record") Option record, @Param("example") Option_Example example);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKey(Option record);
}