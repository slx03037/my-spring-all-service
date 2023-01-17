package com.slx.springboot.mybatis_multidatasource.controller;

import com.slx.springboot.mybatis_multidatasource.service.Student3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 11:23
 **/
@RestController
public class Student3Controller {
    @Autowired
    private Student3Service student3Service;


    @RequestMapping("querystudents3frommysql")
    public List<Map<String, Object>> queryStudentsFromMysql(){
        return this.student3Service.getAllStudentsFromMysql();
    }
}
