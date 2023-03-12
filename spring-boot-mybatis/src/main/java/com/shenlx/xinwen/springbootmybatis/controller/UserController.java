package com.shenlx.xinwen.springbootmybatis.controller;

import com.shenlx.xinwen.springbootmybatis.domain.User;
import com.shenlx.xinwen.springbootmybatis.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 21:29
 **/
@RestController
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping( value = "/queryUser")
    public User queryStudentBySno(String sno) {
        return this.userService.queryStudentBySno(sno);
    }
}
