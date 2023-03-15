package com.shenlx.xinwen.springbootehcache.service.impl;

import com.shenlx.xinwen.springbootehcache.domain.User;
import com.shenlx.xinwen.springbootehcache.mapper.UserMapper;
import com.shenlx.xinwen.springbootehcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-13 21:07
 **/
@Repository
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    @CachePut(key = "#p0.sno")
    public User update(User user) {
        this.userMapper.update(user);
        return this.userMapper.queryBySno(user.getSno());
    }

    @Override
    @CacheEvict(key = "#p0", allEntries = true)
    public void deleteBySno(String sno) {
        this.userMapper.deleteBySno(sno);
    }

    @Override
    @Cacheable(key = "#p0")
    public User queryBySno(String sno) {
        return this.userMapper.queryBySno(sno);
    }
}
