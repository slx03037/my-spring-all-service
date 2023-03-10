package com.shenlx.xinwen.springbootjdbctemplatemultidatasource.controller;

import com.shenlx.xinwen.springbootjdbctemplatemultidatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 16:27
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/querystudentsfrommysql01")
    public List<Map<String, Object>> querystudentsfrommysql01(){
        return this.userService.getUsersMysql01();
    }

    @GetMapping("/querystudentsfrommysql02")
    public List<Map<String, Object>> querystudentsfrommysql02(){
        return this.userService.getUsersMysql02();
    }
}
