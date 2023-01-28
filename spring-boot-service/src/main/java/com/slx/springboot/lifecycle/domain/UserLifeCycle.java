package com.slx.springboot.lifecycle.domain;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 22:22
 **/

public class UserLifeCycle {
    public UserLifeCycle() {
        System.out.println("调用无参构造器创建User");
    }

    public void init() {
        System.out.println("初始化User");
    }

    public void destory() {
        System.out.println("销毁User");
    }
}

