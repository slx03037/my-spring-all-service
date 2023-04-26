package com.shenlx.xinwen.handler;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-26 16:47
 **/

//@ControllerAdvice
//@Order(value = Ordered.HIGHEST_PRECEDENCE)
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(value = AuthorizationException.class)
//    public String handleAuthorizationException() {
//        return "403";
//    }
//}
