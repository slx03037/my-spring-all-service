package com.slx.springboot.common.mapper;

import com.slx.springboot.common.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 11:05
 **/
@Mapper
public interface UserPermissionMapper {

    List<Permission> findByUserName(String userName);
}
