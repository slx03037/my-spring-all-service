package com.shenlx.xinwen.springbootexcetor.excetor.newWorkStealingPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-05 17:23
 **/

public class TestMain {
    /**
     * Executors.newWorkStealingPool()方法同样有两种构造方式，一种为newWorkStealingPool()，这种方式没有参数，
     * 另一种为newWorkStealingPool(int parallelism),这种相比前一种多了并行度参数
     */
    public static void main(String[] args) {
        ExecutorService executor = Executors.newWorkStealingPool();
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
