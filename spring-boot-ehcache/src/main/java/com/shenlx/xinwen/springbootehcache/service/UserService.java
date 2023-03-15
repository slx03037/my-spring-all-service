package com.shenlx.xinwen.springbootehcache.service;

import com.shenlx.xinwen.springbootehcache.domain.User;


public interface UserService {

    User update(User user);


    void deleteBySno(String sno);


    User queryBySno(String sno);
}
