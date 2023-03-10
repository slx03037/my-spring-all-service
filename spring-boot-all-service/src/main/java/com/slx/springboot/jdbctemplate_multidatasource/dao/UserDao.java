package com.slx.springboot.jdbctemplate_multidatasource.dao;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-07 11:33
 **/

public interface UserDao {
    List<Map<String, Object>> getAllStudents();
}
