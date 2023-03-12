package com.shenlx.xinwen.springbootmybatismultidatasource.service.impl;

import com.shenlx.xinwen.springbootmybatismultidatasource.mapper.dev.UserDevMapper;
import com.shenlx.xinwen.springbootmybatismultidatasource.mapper.prod.UserProdMapper;
import com.shenlx.xinwen.springbootmybatismultidatasource.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-12 18:30
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDevMapper userDevMapper;
    @Resource
    private UserProdMapper userProdMapper;


    @Override
    public List<Map<String, Object>> getAllDevs() {
        return userDevMapper.getAlls();
    }

    @Override
    public List<Map<String, Object>> getAllProds() {
        return userProdMapper.getAlls();
    }
}
