package com.slx.springboot.batch.exception.exception;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:43
 **/

public class MyJobExecutionException extends Exception{

    private static final long serialVersionUID = -5975906391606704545L;

    public MyJobExecutionException(String message) {
        super(message);
    }
}
