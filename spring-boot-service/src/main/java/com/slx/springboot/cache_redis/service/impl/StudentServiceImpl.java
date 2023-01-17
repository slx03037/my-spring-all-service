package com.slx.springboot.cache_redis.service.impl;

import com.slx.springboot.cache_redis.service.Student5Service;
import com.slx.springboot.common.entity.Student;
import com.slx.springboot.common.mapper.IStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 22:13
 **/

@Repository("student5Service")
public class StudentServiceImpl implements Student5Service {

    @Autowired
    private IStudentMapper studentMapper;

    @Override
    public Student update(Student student) {
        this.studentMapper.update(student);
        return this.studentMapper.queryStudentBySno(student.getSno());
    }

    @Override
    public void deleteStudentBySno(String sno) {
        this.studentMapper.deleteStudentBySno(sno);
    }

    @Override
    public Student queryStudentBySno(String sno) {
        return this.studentMapper.queryStudentBySno(sno);
    }

}
