package com.shenlx.xinwen.springbootexcetor.excetor.newSingleThreadScheduledExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 16:59
 **/

public class TestMain {
    /**
     * Executors.newSingleThreadScheduledExecutor()方法同样有两种构造方式，一种为newSingleThreadScheduledExecutor()，这种方式没有参数，
     * 另一种为newSingleThreadScheduledExecutor(ThreadFactory threadFactory),这种相比前一种同样是多了一个线程创建方式的参数
     */
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        for (int i = 0; i < 10; i++) {
            int j = i;
            System.out.println("第" + j + "个任务，加入时间为:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("第" + j + "个任务，当前线程名为:" + Thread.currentThread().getName() + "，执行时间为:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            }, 5, TimeUnit.SECONDS);
        }
        /**
         * 分析：由上面的执行结果可以得出其确实实现了定时执行任务的功能，能够发现，任务的执行时间比任务的加入晚了五秒钟，并且每个任务都是如此。
         * PS：其中的ScheduledExecutorService.schedule()方法是具有定时执行功能的线程池特有的方法，表示延迟执行一次定时任务。
         * 另外除了这个之外其还有另外两种，分别是ScheduledExecutorService.scheduleAtFixedRate()和ScheduledExecutorService.scheduleWithFixedDelay()。
         * 其中ScheduledExecutorService.scheduleAtFixedRate()表示固定频率执行，以上次任务的开始时间作为计算时间执行。
         * 而ScheduledExecutorService.scheduleWithFixedDelay()表示固定频率执行但是以上次人物的结束时间为计算时间执行。
         */
    }
}
