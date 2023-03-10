package com.slx.springboot.mybatis.controller;

import com.slx.springboot.common.entity.Student;
import com.slx.springboot.mybatis.service.Student2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 10:22
 **/
@RestController
public class Student2Controller {
    @Autowired
    private Student2Service studentService;

    @RequestMapping( value = "/query2Student", method = RequestMethod.GET)
    public Student queryStudentBySno(String sno) {
        return this.studentService.queryStudentBySno(sno);
    }
}
