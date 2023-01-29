package com.slx.springboot.jwt.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @program: my-spring-all-service
 * @description:  禁用用户异常
 * @author: shenlx
 * @create: 2023-01-28 23:24
 **/

public class ForbiddenUserException extends AuthenticationException {
}
