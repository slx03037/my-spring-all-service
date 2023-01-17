package com.slx.springboot.async.service.impl;

import com.slx.springboot.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 22:52
 **/
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {
    @Override
    @Async
    // @Async
    public Future<String> asyncMethod() {
        sleep();
        log.info("异步方法内部线程名称：{}", Thread.currentThread().getName());
        return new AsyncResult<>("hello async");
    }

    @Override
    public void syncMethod() {
        sleep();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
