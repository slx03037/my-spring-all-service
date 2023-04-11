package com.shenlx.xiwen.springbootinterceptor.controller;

import com.shenlx.xiwen.springbootinterceptor.aspect.RepeatSubmit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-14 15:30
 **/
@RestController
@RequestMapping("/user")
//标注了@RepeatSubmit注解，全部的接口都需要拦截
@RepeatSubmit
public class UserController {
    @GetMapping("/{id:\\d+}")
    public void get(@PathVariable String id) {
        System.out.println(id);
        // throw new RuntimeException("user not exist");
    }


    @RequestMapping("/login")
    public String login(){
        //请求这个URI: http://localhost:8080/springboot-demo/user/login 在5秒之内只能请求一次。
        return "login success";
    }
}
