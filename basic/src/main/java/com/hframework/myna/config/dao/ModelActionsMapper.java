package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.ModelActions;
import com.hframework.myna.config.domain.model.ModelActions_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelActionsMapper {
    int countByExample(ModelActions_Example example);

    int deleteByExample(ModelActions_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(ModelActions record);

    int insertSelective(ModelActions record);

    List<ModelActions> selectByExample(ModelActions_Example example);

    ModelActions selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ModelActions record, @Param("example") ModelActions_Example example);

    int updateByExample(@Param("record") ModelActions record, @Param("example") ModelActions_Example example);

    int updateByPrimaryKeySelective(ModelActions record);

    int updateByPrimaryKey(ModelActions record);
}