package com.shenlx.xinwen.jwt.cache.model;

import com.shenlx.xinwen.jwt.cache.domain.Permission;
import com.shenlx.xinwen.jwt.cache.domain.Role;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-04 14:59
 **/

public class SysRole extends Role {
    private static final long serialVersionUID = 9192187550019588933L;
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
