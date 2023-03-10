package com.slx.springboot.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 19:25
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut(value = "@annotation(com.slx.springboot.aop.aspect.LogMark)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        log.info("second before入参：{}",joinPoint);
    }

    @Around("@annotation(logMark)")
    public Object around(ProceedingJoinPoint pjp, LogMark logMark) {
        //获取注解里的值
        log.info("second around:{}" , logMark.desc());
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
