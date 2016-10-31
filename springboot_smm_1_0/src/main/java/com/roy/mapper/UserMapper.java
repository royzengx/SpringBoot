/**
 * 
 */
package com.roy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.roy.domain.User;

/**
 * @author Roy
 *
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findUserByName(@Param("name") String name);
    
    @Select("SELECT * FROM USER")
    List<User> findAll();
    
    @Update("update user set name = #{name} where name = #{name}")
    void updateUser(User user);
}