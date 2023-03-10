package com.slx.springboot.aop.aspect;

import java.lang.annotation.*;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 19:21
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface LogMark {
    String desc() default "无信息";
}
