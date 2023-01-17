package com.slx.springboot.jdbctemplate_multidatasource.controller;

import com.slx.springboot.jdbctemplate_multidatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-07 21:51
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

//    @RequestMapping("querystudentsfromoracle")
//    public List<Map<String, Object>> queryStudentsFromOracle(){
//        return this.studentService.getAllStudentsFromOralce();
//    }

    @RequestMapping("querystudentsfrommysql")
    public List<Map<String, Object>> queryStudentsFromMysql(){
        return this.userService.getAllStudentsFromMysql();
    }
}
