package com.shenlx.xinwen.springbootcondition.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-12 16:43
 **/
@Data
public class Person implements Serializable {
    private String name;
    private Integer age;
    private String sex;

}
