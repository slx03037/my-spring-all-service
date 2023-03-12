package com.shenlx.xinwen.springbootaop.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 20:10
 **/

public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
