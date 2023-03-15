package com.shenlx.xinwen.springbootehcache.mapper;

import com.shenlx.xinwen.springbootehcache.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Update("update user set sname=#{name},ssex=#{sex} where sno=#{sno}")
    int update(User user);

    @Delete("delete from user where sno=#{sno}")
    void deleteBySno(String sno);

    @Select("select * from user where sno=#{sno}")
    @Results(id = "student", value = { @Result(property = "sno", column = "sno", javaType = String.class),
            @Result(property = "name", column = "sname", javaType = String.class),
            @Result(property = "sex", column = "ssex", javaType = String.class) })
    User queryBySno(String sno);
}
