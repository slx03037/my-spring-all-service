package com.slx.springboot.batch.listener.listener;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:38
 **/
@Component
public class MyItemWriterListener implements ItemWriteListener<String> {
    @Override
    public void beforeWrite(List<? extends String> items) {
        System.out.println("before write: " + items);
    }

    @Override
    public void afterWrite(List<? extends String> items) {
        System.out.println("after write: " + items);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends String> items) {
        System.out.println("on write error: " + items + " , error message: " + exception.getMessage());
    }
}
