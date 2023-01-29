package com.slx.springboot.shiro.authentication.mapper;

import com.slx.springboot.shiro.authentication.pojo.UserShiro;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserShiroMapper {
    UserShiro findByUserName(String userName);
}
