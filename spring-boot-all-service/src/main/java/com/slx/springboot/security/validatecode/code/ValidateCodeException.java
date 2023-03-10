package com.slx.springboot.security.validatecode.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-31 11:32
 **/

public class ValidateCodeException extends AuthenticationException {

    private static final long serialVersionUID = -8477961516017571370L;

    public ValidateCodeException(String message) {
        super(message);
    }
}