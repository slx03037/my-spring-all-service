package com.slx.springboot.async.controller;

import com.slx.springboot.async.service.AsyncService;
import com.slx.springboot.async.until.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 22:50
 **/
@RestController
@Slf4j
public class AsyncController {
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private AsyncTask asyncTask;

    @GetMapping("async")
    public String testAsync() throws Exception {
        long start = System.currentTimeMillis();
        log.info("异步方法开始");
        asyncTask.dealNoReturnTask();
        Future<String> stringFuture1 = asyncTask.dealHaveReturnTask(1);
        String result2 = stringFuture1.get(60, TimeUnit.SECONDS);
        Future<String> stringFuture = asyncService.asyncMethod();
        String result = stringFuture.get(60, TimeUnit.SECONDS);
        log.info("异步方法2返回值：{}", result2);
        log.info("异步方法返回值：{}", result);

        log.info("异步方法结束");

        long end = System.currentTimeMillis();
        log.info("总耗时：{} ms", end - start);
        return stringFuture.get();
    }

    @GetMapping("sync")
    public void testSync() {
        long start = System.currentTimeMillis();
        log.info("同步方法开始");

        asyncService.syncMethod();

        log.info("同步方法结束");
        long end = System.currentTimeMillis();
        log.info("总耗时：{} ms", end - start);
    }

}
