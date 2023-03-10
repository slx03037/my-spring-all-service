package com.slx.springboot.shiro.jwt.controller;

import com.slx.springboot.jwt.util.JWTUtil;
import com.slx.springboot.shiro.jwt.domain.Response;
import com.slx.springboot.shiro.jwt.domain.UserJWTShiroDO;
import com.slx.springboot.shiro.jwt.exception.SystemException;
import com.slx.springboot.shiro.jwt.properties.SystemProperties;
import com.slx.springboot.shiro.jwt.util.MD5JWTUtil;
import com.slx.springboot.shiro.jwt.util.SystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 23:00
 **/
@RestController
@Validated
public class LoginController {
    @Autowired
    private SystemProperties properties;

    @PostMapping("/login")
    public Response login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password, HttpServletRequest request) throws Exception {
        username = StringUtils.lowerCase(username);
        password = MD5JWTUtil.encrypt(username, password);

        final String errorMessage = "用户名或密码错误";
        UserJWTShiroDO user = SystemUtils.getUser(username);

        if (user == null)
            throw new SystemException(errorMessage);
        if (!StringUtils.equals(user.getPassword(), password))
            throw new SystemException(errorMessage);

        // 生成 Token
        String token = JWTUtil.sign(username, password);

        Map<String, Object> userInfo = this.generateUserInfo(token, user);
        return new Response().message("认证成功").data(userInfo);
    }

    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. user
     *
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(String token, UserJWTShiroDO user) {
        String username = user.getUsername();
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("token", token);

        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }
}
