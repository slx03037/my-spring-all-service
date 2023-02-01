package com.slx.springboot.common.mapper;

import com.slx.springboot.common.entity.UserShiro;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserShiroMapper {
    UserShiro findByUserName(String userName);
}
