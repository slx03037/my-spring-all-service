package com.shenlx.xinwen.springbootexcetor.excetor.newFixedThreadPool;

import java.util.concurrent.*;

/**
 * @program: learn-demo
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 11:16
 **/

/**
 * 创建一个固定大小的线程池，可控制并发的线程数。
 */

public class FixedThreadPoolDemo {
    private final BlockingQueue<Runnable> workQueue;
    private volatile RejectedExecutionHandler handler;
    private volatile ThreadFactory threadFactory;
    private volatile int corePoolSize;
    private volatile int maximumPoolSize;
    private volatile long keepAliveTime;



    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public FixedThreadPoolDemo(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), defaultHandler);
    }

    private static final RejectedExecutionHandler defaultHandler =
            new ThreadPoolExecutor.AbortPolicy();

    public FixedThreadPoolDemo(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

}
