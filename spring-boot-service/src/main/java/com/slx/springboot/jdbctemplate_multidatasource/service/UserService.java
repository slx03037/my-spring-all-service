package com.slx.springboot.jdbctemplate_multidatasource.service;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<Map<String, Object>> getAllStudentsFromMysql();
}
