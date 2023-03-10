package com.slx.springboot.jwt.model;

import com.slx.springboot.jwt.domain.Permission;
import com.slx.springboot.jwt.domain.Role;

import java.io.Serializable;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description: 角色信息
 * @author: shenlx
 * @create: 2023-01-28 23:32
 **/

public class SysRole extends Role implements Serializable {
    // 拥有的权限列表
    private List<Permission> permissions;

    public SysRole() {
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
