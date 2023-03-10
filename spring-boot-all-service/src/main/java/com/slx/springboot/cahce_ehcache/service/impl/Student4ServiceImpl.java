package com.slx.springboot.cahce_ehcache.service.impl;

import com.slx.springboot.common.entity.Student;
import com.slx.springboot.common.mapper.IStudentMapper;
import com.slx.springboot.cahce_ehcache.service.Student4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 20:52
 **/
@Repository
public class Student4ServiceImpl implements Student4Service {
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
