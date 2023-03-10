package com.shenlx.xinwen.springbootjdbctemplate.service.impl;

import com.shenlx.xinwen.springbootjdbctemplate.dao.UserDao;
import com.shenlx.xinwen.springbootjdbctemplate.domain.User;
import com.shenlx.xinwen.springbootjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 14:58
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public int add(User user) {
        return this.userDao.add(user);
    }

    @Override
    public int update(User user) {
        return this.userDao.update(user);
    }

    @Override
    public int deleteBysno(String sno) {
        return  this.userDao.deleteBysno(sno);
    }

    @Override
    public List<Map<String, Object>> queryMap() {
        return this.userDao.queryusersListMap();
    }

    @Override
    public User queryBySno(String sno) {
        return this.userDao.queryUserBySno(sno);
    }
}
