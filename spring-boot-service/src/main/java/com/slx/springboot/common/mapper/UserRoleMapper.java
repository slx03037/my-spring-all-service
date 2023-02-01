package com.slx.springboot.common.mapper;

import com.slx.springboot.common.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    List<Role> findByUserName(String userName);
}
