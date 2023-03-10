package com.slx.springboot.mybatis.service.impl;

import com.slx.springboot.common.entity.Student;
import com.slx.springboot.mybatis.mapper.MStudentMapper;
import com.slx.springboot.mybatis.service.Student2Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 10:15
 **/
@Service
public class Student2ServiceImp implements Student2Service {
    @Resource
    private MStudentMapper mStudentMapper;

    @Override
    public int add(Student student) {
        return this.mStudentMapper.add(student);
    }

    @Override
    public int update(Student student) {
        return this.mStudentMapper.update(student);
    }

    @Override
    public int deleteBysno(String sno) {
        return this.mStudentMapper.deleteBysno(sno);
    }

    @Override
    public Student queryStudentBySno(String sno) {
        return this.mStudentMapper.queryStudentBySno(sno);
    }
}
