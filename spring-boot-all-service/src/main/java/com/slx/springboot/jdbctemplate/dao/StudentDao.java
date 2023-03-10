package com.slx.springboot.jdbctemplate.dao;


import com.slx.springboot.common.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentDao {
    int add(Student student);
    int update(Student student);
    int deleteBysno(String sno);
    List<Map<String,Object>> queryStudentsListMap();
    Student queryStudentBySno(String sno);
}
