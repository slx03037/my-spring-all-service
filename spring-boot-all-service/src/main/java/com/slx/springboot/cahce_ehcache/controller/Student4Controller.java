package com.slx.springboot.cahce_ehcache.controller;

import com.slx.springboot.common.entity.Student;
import com.slx.springboot.cahce_ehcache.service.Student4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 20:52
 **/
@RestController
public class Student4Controller {
    @Autowired
    private Student4Service student4Service;
    @RequestMapping("querystudents4frommysql")
    public Student queryStudentsFromMysql(String sno){
        return this.student4Service.queryStudentBySno(sno);
    }
}
