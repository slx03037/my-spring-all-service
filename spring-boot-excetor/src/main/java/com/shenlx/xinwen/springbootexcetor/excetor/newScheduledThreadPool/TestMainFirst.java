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
 * @create: 2023-07-05 17:02
 **/

public class TestMainFirst {
    /**
     * Executors.newScheduledThreadPool()方法同样有两种构造方式，一种为newScheduledThreadPool(int corePoolSize)，这种方式只有一个参数表示会创建的线程数量，
     * 另一种为newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory),这种相比前一种同样是多了一个线程创建方式的参数。
     */
    // schedule方式
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        for (int i = 0; i < 10; i++) {
            int j = i;
            System.out.println("第" + j + "个任务，加入时间为：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第" + j + "个任务，当前线程名称为：" + Thread.currentThread().getName() + "，当前执行时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            }, 5, TimeUnit.SECONDS);
        }

    }

}
