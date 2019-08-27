package com.hframework.myna.config.dao;

import com.hframework.myna.config.domain.model.P2pReply;
import com.hframework.myna.config.domain.model.P2pReply_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface P2pReplyMapper {
    int countByExample(P2pReply_Example example);

    int deleteByExample(P2pReply_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(P2pReply record);

    int insertSelective(P2pReply record);

    List<P2pReply> selectByExample(P2pReply_Example example);

    P2pReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") P2pReply record, @Param("example") P2pReply_Example example);

    int updateByExample(@Param("record") P2pReply record, @Param("example") P2pReply_Example example);

    int updateByPrimaryKeySelective(P2pReply record);

    int updateByPrimaryKey(P2pReply record);
}