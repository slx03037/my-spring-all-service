package com.shenlx.xinwen.springbootexcetor.excetor.newScheduledThreadPool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 17:04
 **/

public class TestMainThird {
    // scheduleWithFixedDelay方式
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        // 以固定频率执行任务，其中initialDelay表示第一次执行与加入时间间隔多久，period表示两次执行之间的间隔时间为多少，也就是频率。
        // 执行间隔时间按照睡眠时间加上间隔时间
        System.out.println("加入时间为：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程名称为：" + Thread.currentThread().getName() + "，当前执行时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 3, 5, TimeUnit.SECONDS);

    }
}
