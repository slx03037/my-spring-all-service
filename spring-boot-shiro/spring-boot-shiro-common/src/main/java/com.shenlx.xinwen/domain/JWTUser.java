package com.shenlx.xinwen.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-04 10:13
 **/

public class JWTUser implements Serializable {
    private static final long serialVersionUID = -4936865762120454493L;
    private String username;

    private String password;

    private Set<String> role;

    private Set<String> permission;

    public JWTUser(String username, String password, Set<String> role, Set<String> permission) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

    public Set<String> getPermission() {
        return permission;
    }

    public void setPermission(Set<String> permission) {
        this.permission = permission;
    }
}
