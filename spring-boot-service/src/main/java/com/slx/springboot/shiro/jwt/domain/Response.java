package com.slx.springboot.shiro.jwt.domain;

import java.util.HashMap;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 22:35
 **/

public class Response extends HashMap<String, Object> {
    private static final long serialVersionUID = 7165765942190404933L;

    public Response message(String message) {
        this.put("message", message);
        return this;
    }

    public Response data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
