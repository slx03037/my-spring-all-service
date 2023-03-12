package com.shenlx.xinwen.springbootmybatis.service.impl;

import com.shenlx.xinwen.springbootmybatis.domain.User;
import com.shenlx.xinwen.springbootmybatis.mapper.UserMapper;
import com.shenlx.xinwen.springbootmybatis.service.UserService;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 21:28
 **/

public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public int add(User user) {
        return userMapper.add(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int deleteBysno(String sno) {
        return userMapper.deleteBysno(sno);
    }

    @Override
    public User queryStudentBySno(String sno) {
        return userMapper.queryUserBySno(sno);
    }
}
