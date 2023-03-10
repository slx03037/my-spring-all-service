package com.slx.springboot.shiro.session.service;

import com.slx.springboot.shiro.session.pojo.UserOnline;

import java.util.List;

public interface SessionService {
    List<UserOnline> list();
    boolean forceLogout(String sessionId);
}
