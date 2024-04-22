package com.shenlx.xinwen.rxjava.entity;

import lombok.Data;

/**
 * @author shenlx
 * @description
 * @date 2024/4/19 9:37
 */
@Data
public class User {
    private String id;
    private String name;
    private String age;

    public User() {
    }

    public User(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
