package com.shenlx.xinwen.springbootmybatis.mapper;

import com.shenlx.xinwen.springbootmybatis.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 21:20
 **/

@Mapper
public interface UserMapper {

    @Insert("insert into user(sno,sname,ssex) values(#{sno},#{name},#{sex})")
    int add(User user);

    @Update("update user set sname=#{name},ssex=#{sex} where sno=#{sno}")
    int update(User user);

    @Delete("delete from user where sno=#{sno}")
    int deleteBysno(String sno);

    @Select("select * from User where sno=#{sno}")
    @Results(id = "User",value= {
            @Result(property = "sno", column = "sno", javaType = String.class),
            @Result(property = "name", column = "sname", javaType = String.class),
            @Result(property = "sex", column = "ssex", javaType = String.class)
    })
    User queryUserBySno(String sno);
}
