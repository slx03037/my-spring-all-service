package com.shenlx.xinwen.springbootjdbctemplatemultidatasource.service.impl;

import com.shenlx.xinwen.springbootjdbctemplatemultidatasource.dao.mysql01.SysUserDao;
import com.shenlx.xinwen.springbootjdbctemplatemultidatasource.dao.mysql02.SysUser02Dao;
import com.shenlx.xinwen.springbootjdbctemplatemultidatasource.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 16:25
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUser02Dao sysUser02Dao;

    @Override
    public List<Map<String, Object>> getUsersMysql01() {
        return this.sysUserDao.getUsers();
    }

    @Override
    public List<Map<String, Object>> getUsersMysql02() {
        return this.sysUser02Dao.getUsers();
    }
}
