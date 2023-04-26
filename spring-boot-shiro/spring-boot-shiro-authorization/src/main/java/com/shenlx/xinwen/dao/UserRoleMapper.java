package com.shenlx.xinwen.dao;

import java.util.List;

import com.shenlx.xinwen.domain.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
	
	List<Role> findByUserName(String userName);
}
