package com.shenlx.xinwen.springbootexcetor.excetor.newCachedThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 16:32
 **/

public class TestMain {
    public static void main(String[] args) {
        /**
         * Executors.newCachedThreadPool()方法同样有两种构造方式，一种为newCachedThreadPool()，这种方式没有参数
         * ，另一种为newCachedThreadPool(ThreadFactory threadFactory),这种相比前一种同样是多了一个线程创建方式的参数
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            int j = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第" + j + "个任务，当前线程的名称：" + Thread.currentThread().getName());
                }
            });
        }
        /**
         * 由上面循环十次的执行结果和循环一百次的执行结果可以看出其线程是有缓存的，当来个新的任务是，如果当前没有空闲的线程，则会创建新的线程来执行新任务，
         * 如果此时有空闲的线程，则会使用这个空闲的线程来执行任务。另外当这个线程池中的任务都处于空闲，并达到一定的时间时，则会将该线程池回收，
         * 因此这就是这个线程池的程序是会结束，而其它线程池的程序不会结束的原因，另外，线程的空闲时间默认为60秒。
         */
    }
}
