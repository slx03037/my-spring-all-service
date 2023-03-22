package com.shenlx.xinwen.springbootadminserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-22 10:46
 **/
@RestController
public class UserContoller {
    @GetMapping("getUsername")
    public String getUsername(){
        return "张三";
    }

    @PostMapping("getUsername")
    public String geUser(){
        return "张三";
    }
}
