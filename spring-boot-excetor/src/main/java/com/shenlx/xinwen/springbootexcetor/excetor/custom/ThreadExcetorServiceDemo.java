package com.shenlx.xinwen.springbootexcetor.excetor.custom;

/**
 * @program: learn-demo
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 10:04
 **/

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * 各参数含义
 * corePoolSize    : 线程池中常驻的线程数量。核心线程数，默认情况下核心线程会一直存活，即使处于闲置状态也不会
 *                   受存活时间 keepAliveTime 的限制，除非将 allowCoreThreadTimeOut 设置为 true。
 * maximumPoolSize : 线程池所能容纳的最大线程数。超过这个数的线程将被阻塞。当任务队列为没有设置大小的
 *                           LinkedBlockingQueue时，这个值无效。
 * keepAliveTime   : 当线程数量多于 corePoolSize 时，空闲线程的存活时长，超过这个时间就会被回收
 * unit            : keepAliveTime 的时间单位
 * workQueue       : 存放待处理任务的队列
 * threadFactory   : 线程工厂
 * handler         : 拒绝策略，拒绝无法接收添加的任务
 */
public class ThreadExcetorServiceDemo {
    private static final int CORE_POOL_SIZE=5;//核心线程数为
    private static final int  MAX_POOL_SIZE=10; //最大线程数
    private static final Long KEEP_ALIVE_TIME=2L;//等待时间为 1L
    private static final int QUEUE_CAPACITY=100;//任务队列为 ArrayBlockingQueue，并且容量为 100


    //使用阿里巴巴推荐的创建线程池的方式
    //通过ThreadPoolExecutor构造函数自定义参数创建
    ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(
            CORE_POOL_SIZE
            ,MAX_POOL_SIZE
            ,KEEP_ALIVE_TIME
            ,TimeUnit.SECONDS
            ,new ArrayBlockingQueue<>(QUEUE_CAPACITY)
            ,new ThreadPoolExecutor.CallerRunsPolicy()
    );
    public void threadHandler() {
        Integer count = 1;
        for (int i = 0; i < 10; i++) {
            String s = count + "s";
            count++;
            Runnable runnable = new MyRunable(s);
            threadPoolExecutor.execute(runnable);
            //终止线程池
            //threadPoolExecutor.shutdown();
//            while (!threadPoolExecutor.isTerminated()) {
//                System.out.println("excete isTerminated");
//            }
            System.out.println("Finished all threads");
        }
        threadPoolExecutor.shutdown();

    }
}
