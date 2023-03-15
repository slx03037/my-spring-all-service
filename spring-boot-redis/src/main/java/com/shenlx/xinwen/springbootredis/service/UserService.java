package com.shenlx.xinwen.springbootredis.service;

import com.shenlx.xinwen.springbootredis.domain.User;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-15 22:01
 **/

public interface UserService {

    void createUser(User user);

    User getById(int id);

    void updateUser(User user);

    void deleteById(int id);

}
