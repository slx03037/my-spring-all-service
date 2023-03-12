package com.shenlx.xinwen.springbootmybatismultidatasource.controller;

import com.shenlx.xinwen.springbootmybatismultidatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 18:32
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("getAllDevs")
    public List<Map<String, Object>> getAllDevs(){
        return this.userService.getAllDevs();
    }

    @RequestMapping("getAllProds")
    public List<Map<String, Object>> getAllProds(){
        return this.userService.getAllProds();
    }
}
