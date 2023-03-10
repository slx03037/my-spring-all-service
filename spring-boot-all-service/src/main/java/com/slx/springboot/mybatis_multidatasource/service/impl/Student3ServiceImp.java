package com.slx.springboot.mybatis_multidatasource.service.impl;

import com.slx.springboot.common.mapper.MysqlStudentMapper;
import com.slx.springboot.mybatis_multidatasource.service.Student3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 11:21
 **/
@Service
public class Student3ServiceImp implements Student3Service {
//    @Autowired
//    private OracleStudentMapper oracleStudentMapper;
    @Resource
    private MysqlStudentMapper mysqlStudentMapper;

//    @Override
//    public List<Map<String, Object>> getAllStudentsFromOralce() {
//        return this.oracleStudentMapper.getAllStudents();
//    }

    @Override
    public List<Map<String, Object>> getAllStudentsFromMysql() {
        return this.mysqlStudentMapper.getAllStudents();
    }
}
