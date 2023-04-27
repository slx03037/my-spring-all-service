package com.shenlx.xinwen.dao;

import com.shenlx.xinwen.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
	User findByUserName(@Param("username") String username);
}
