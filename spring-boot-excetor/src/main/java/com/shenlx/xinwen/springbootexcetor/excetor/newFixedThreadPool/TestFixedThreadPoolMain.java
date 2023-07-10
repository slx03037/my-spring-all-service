package com.shenlx.xinwen.springbootexcetor.excetor.newFixedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: learn-demo
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 11:19
 **/

public class TestFixedThreadPoolMain {
    public  static void main(String[]args) {
//        ExecutorService executorService = new ExecutorService();
//        Executors.callable(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        ExecutorService threadPool = FixedThreadPoolDemo.newFixedThreadPool(2);
        // 创建任务
        Runnable runnable = () -> System.out.println("任务被执行,线程:" + Thread.currentThread().getName());
        // 线程池执行任务(一次添加 8 个任务)
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.execute(runnable);
        threadPool.shutdown();

        // 创建 2 个线程的线程池
        ExecutorService threadPools = Executors.newFixedThreadPool(2);
        threadPools.execute(runnable);

    }
}
