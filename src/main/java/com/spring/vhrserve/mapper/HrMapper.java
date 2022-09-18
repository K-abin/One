package com.spring.vhrserve.mapper;

import com.spring.vhrserve.bean.Hr;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr loginUsername(String username);
}