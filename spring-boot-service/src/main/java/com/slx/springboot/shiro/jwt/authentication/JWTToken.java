package com.slx.springboot.shiro.jwt.authentication;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 22:54
 **/

public class JWTToken implements AuthenticationToken {
    private static final long serialVersionUID = 1282057025599826155L;

    private String token;

    private String exipreAt;

    public JWTToken(String token) {
        this.token = token;
    }

    public JWTToken(String token, String exipreAt) {
        this.token = token;
        this.exipreAt = exipreAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExipreAt() {
        return exipreAt;
    }

    public void setExipreAt(String exipreAt) {
        this.exipreAt = exipreAt;
    }
}
