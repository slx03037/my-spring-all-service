package com.shenlx.xinwen.springbootexcetor.excetor.newSingleThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 16:40
 **/

public class TestMain {
    /**
     * 由上面的结果可以看出该线程池只会有一个线程来进行执行任务，而执行任务的顺序是有序的，会按照先进先出的顺序来进行执行。这里虽然线程池只有一个线程和创建线程数量一样，但是其避免了频繁创建销毁线程的开销，实现了这一个线程的复用由上面的结果可以看出该线程池只会有一个线程来进行执行任务，而执行任务的顺序是有序的，会按照先进先出的顺序来进行执行
     * 。这里虽然线程池只有一个线程和创建线程数量一样，但是其避免了频繁创建销毁线程的开销，实现了这一个线程的复用
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int j = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第" + j + "个任务，当前线程名为:" + Thread.currentThread().getName());
                }
            });
        }

    }
}
