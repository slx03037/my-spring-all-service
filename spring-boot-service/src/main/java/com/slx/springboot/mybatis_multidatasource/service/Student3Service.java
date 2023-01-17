package com.slx.springboot.mybatis_multidatasource.service;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 11:21
 **/

public interface Student3Service {
    List<Map<String, Object>> getAllStudentsFromMysql();
}
