package com.slx.springboot.batch.listener.listener;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:37
 **/
@Component
public class MyItemProcessListener  implements ItemProcessListener<String, String> {
    @Override
    public void beforeProcess(String item) {
        System.out.println("before process: " + item);
    }

    @Override
    public void afterProcess(String item, String result) {
        System.out.println("after process: " + item + " result: " + result);
    }

    @Override
    public void onProcessError(String item, Exception e) {
        System.out.println("on process error: " + item + " , error message: " + e.getMessage());
    }
}
