package com.roy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.roy.domain.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String email);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String email);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> find(User user);
    
    int findCount(User user);
}