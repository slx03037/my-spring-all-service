package com.slx.springboot.exception.exception;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-10 22:43
 **/

public class UserNotExistException extends RuntimeException{

    private static final long serialVersionUID = 7575158509873350262L;

    private String id;

    public UserNotExistException(String id){
        super("user not exist");
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
