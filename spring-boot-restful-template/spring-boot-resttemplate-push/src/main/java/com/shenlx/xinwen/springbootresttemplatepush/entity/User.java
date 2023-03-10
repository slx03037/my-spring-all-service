package com.shenlx.xinwen.springbootresttemplatepush.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-10 13:07
 **/
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 8784786156844071454L;
    private String name;
    private Integer age;
    private byte sex;

}
