package com.shenlx.xinwen.springbootrediscache.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenlx.xinwen.springbootrediscache.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-14 16:56
 **/
@Mapper
public interface UserMapper {

    @Select("select * from t_user where id=#{id}")
    @Results(id = "User",value= {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class)
    })
    User getById(int id);

    @Select("select * from t_user")
    @Results(id = "Users",value= {
            @Result(property = "id", column = "id", javaType = Integer.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "password", column = "password", javaType = String.class)
    })
    List<User> getAllUsers();
}
