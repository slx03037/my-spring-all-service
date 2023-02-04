package com.shenlx.xinwen.springbootresttemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-04 15:23
 **/
@Component
@Slf4j
public class LoginConfig implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        log.info("request:{}",request);
        return true;
    }
}
