package com.slx.springboot.shiro.jwt.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 22:33
 **/
@Data
public class UserJWTShiroDO implements Serializable {
    private static final long serialVersionUID = -838616378538056019L;
    private String username;

    private String password;

    private Set<String> role;

    private Set<String> permission;

    public UserJWTShiroDO(String username, String password, Set<String> role, Set<String> permission) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.permission = permission;
    }
}
