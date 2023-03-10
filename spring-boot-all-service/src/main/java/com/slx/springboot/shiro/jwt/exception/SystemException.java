package com.slx.springboot.shiro.jwt.exception;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 22:36
 **/

public class SystemException extends Exception {
    private static final long serialVersionUID = -5474176320133519457L;

    public SystemException(String message) {
        super(message);
    }
}
