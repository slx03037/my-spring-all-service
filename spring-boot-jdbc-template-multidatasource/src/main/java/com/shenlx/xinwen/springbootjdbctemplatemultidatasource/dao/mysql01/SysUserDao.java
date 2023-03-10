package com.shenlx.xinwen.springbootjdbctemplatemultidatasource.dao.mysql01;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 15:55
 **/

public interface SysUserDao {
    List<Map<String, Object>> getUsers();
}
