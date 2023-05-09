package com.shenlx.xinwen.spring.security.validate.code.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-27 17:12
 **/

public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
