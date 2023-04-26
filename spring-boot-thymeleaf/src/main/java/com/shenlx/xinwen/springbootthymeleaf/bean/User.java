package com.shenlx.xinwen.springbootthymeleaf.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-25 15:01
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String skills;
    private int age;
    private Date birthday;
    private List<String> list;
}

