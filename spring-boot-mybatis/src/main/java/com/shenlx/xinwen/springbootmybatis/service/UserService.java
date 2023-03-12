package com.shenlx.xinwen.springbootmybatis.service;

import com.shenlx.xinwen.springbootmybatis.domain.User;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 21:26
 **/

public interface UserService {
    int add(User user);
    int update(User user);
    int deleteBysno(String sno);
    User queryStudentBySno(String sno);
}
