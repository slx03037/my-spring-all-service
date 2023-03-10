package com.slx.springboot.shiro.jwt.util;

import com.slx.springboot.shiro.jwt.domain.UserJWTShiroDO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-30 22:33
 **/

public class SystemUtils {
    private static Logger log = LoggerFactory.getLogger(SystemUtils.class);

    /**
     * 模拟两个用户
     *
     * @return List<User>
     */
    private static List<UserJWTShiroDO> users() {
        List<UserJWTShiroDO> users = new ArrayList<>();
        // 模拟两个用户：
        // 1. 用户名 admin，密码 123456，角色 admin（管理员），权限 "user:add"，"user:view"
        // 1. 用户名 scott，密码 123456，角色 regist（注册用户），权限 "user:view"
        users.add(new UserJWTShiroDO(
                "admin",
                "bfc62b3f67a4c3e57df84dad8cc48a3b",
                new HashSet<>(Collections.singletonList("admin")),
                new HashSet<>(Arrays.asList("user:add", "user:view"))));
        users.add(new UserJWTShiroDO(
                "scott",
                "11bd73355c7bbbac151e4e4f943e59be",
                new HashSet<>(Collections.singletonList("regist")),
                new HashSet<>(Collections.singletonList("user:view"))));
        return users;
    }

    /**
     * 获取用户
     *
     * @param username 用户名
     * @return 用户
     */
    public static UserJWTShiroDO getUser(String username) {
        List<UserJWTShiroDO> users = SystemUtils.users();
        return users.stream().filter(user -> StringUtils.equalsIgnoreCase(username, user.getUsername())).findFirst().orElse(null);
    }
}
