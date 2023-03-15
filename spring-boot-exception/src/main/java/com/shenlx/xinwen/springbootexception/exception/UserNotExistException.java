package com.shenlx.xinwen.springbootexception.exception;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-13 22:30
 **/

public class UserNotExistException extends RuntimeException{
    private static final long serialVersionUID = -3220671751018002004L;
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
