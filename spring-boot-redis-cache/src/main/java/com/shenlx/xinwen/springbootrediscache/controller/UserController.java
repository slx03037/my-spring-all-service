package com.shenlx.xinwen.springbootrediscache.controller;

import com.shenlx.xinwen.springbootrediscache.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-14 17:05
 **/
@RestController
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/getById")
    public Object getById(Integer id){
        return userService.getById(id);
    }

    @GetMapping("/getAllUsers")
    public Object getAllUsers(){
        return userService.getAllUsers();
    }
}
