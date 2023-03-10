package com.slx.springboot.mybatis.service;

import com.slx.springboot.common.entity.Student;

public interface Student2Service {
    int add(Student student);
    int update(Student student);
    int deleteBysno(String sno);
    Student queryStudentBySno(String sno);
}
