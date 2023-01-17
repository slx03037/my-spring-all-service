package com.slx.springboot.async.until;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 22:47
 **/
@Component
@Slf4j
public class AsyncTask {
    @Async
    public void dealNoReturnTask() {
        log.info("返回值为void的异步调用开始" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("返回值为void的异步调用结束" + Thread.currentThread().getName());
    }

    @Async
    public Future<String> dealHaveReturnTask(int i) {
        log.info("返回值为Future的异步调用开始" + Thread.currentThread().getName());
        log.info("asyncInvokeReturnFuture, parementer=" + i);
        Future<String> future;
        try {
            Thread.sleep(1000 * i);
            future = new AsyncResult<String>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        }
        log.info("返回值为Future的异步调用结束" + Thread.currentThread().getName());
        return future;
    }


}
