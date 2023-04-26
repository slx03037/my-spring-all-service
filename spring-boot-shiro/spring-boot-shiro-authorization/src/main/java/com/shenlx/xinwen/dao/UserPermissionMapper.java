package com.shenlx.xinwen.dao;

import java.util.List;

import com.shenlx.xinwen.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPermissionMapper {
	
	List<Permission> findByUserName(String userName);
}
