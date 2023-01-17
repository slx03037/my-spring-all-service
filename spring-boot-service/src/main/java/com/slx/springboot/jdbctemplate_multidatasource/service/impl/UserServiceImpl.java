package com.slx.springboot.jdbctemplate_multidatasource.service.impl;

import com.slx.springboot.jdbctemplate_multidatasource.dao.UserDao;
import com.slx.springboot.jdbctemplate_multidatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-07 21:49
 **/
@Service
public class UserServiceImpl implements UserService {
//    @Autowired
//    private OracleStudentDao oracleStudentDao;
    @Autowired
    private UserDao userDao;

//    @Override
//    public List<Map<String, Object>> getAllStudentsFromOralce() {
//        return this.oracleStudentDao.getAllStudents();
//    }

    @Override
    public List<Map<String, Object>> getAllStudentsFromMysql() {
        return this.userDao.getAllStudents();
    }
}
