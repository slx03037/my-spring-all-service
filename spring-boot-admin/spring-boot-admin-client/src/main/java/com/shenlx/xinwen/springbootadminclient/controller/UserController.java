package com.shenlx.xinwen.springbootadminclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-22 09:32
 **/
@RestController
public class UserController {
    @GetMapping("/getUsername")
    public String getUsername(){
        return "张三";
    }
}
