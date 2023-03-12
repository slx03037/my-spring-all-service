package com.shenlx.xinwen.springbootasync.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 20:32
 **/
@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Async method has uncaught exception, params:{}" + Arrays.toString(params));

        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            log.error("asyncException:" + asyncException.getMessage());
        }

        log.error("Exception :", ex);
    }
}
