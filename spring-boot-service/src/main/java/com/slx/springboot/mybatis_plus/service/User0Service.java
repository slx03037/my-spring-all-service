package com.slx.springboot.mybatis_plus.service;

import com.slx.springboot.common.entity.User;
import com.slx.springboot.common.repository.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-09 16:20
 **/

@Service
public class User0Service {

    @Resource
    private UserMapper userMapper;

    /**
     * 通过ID查找用户
     * @param id
     * @return
     */
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * 新增用户
     * @param user
     */
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    /**
     * 修改用户
     * @param user
     */
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Integer id) {
        userMapper.deleteById(id);
    }

}
