package com.shenlx.xinwen.jwt.untils;

import com.shenlx.xinwen.domain.JWTUser;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-04 10:10
 **/
@Slf4j
public class SystemUtils {
    /**
     * 模拟两个用户
     *
     * @return List<User>
     */
    private static List<JWTUser> users() {
        List<JWTUser> users = new ArrayList<>();
        // 模拟两个用户：
        // 1. 用户名 admin，密码 123456，角色 admin（管理员），权限 "user:add"，"user:view"
        // 1. 用户名 scott，密码 123456，角色 regist（注册用户），权限 "user:view"
        users.add(new JWTUser(
                "admin",
                "bfc62b3f67a4c3e57df84dad8cc48a3b",
                new HashSet<>(Collections.singletonList("admin")),
                new HashSet<>(Arrays.asList("user:add", "user:view"))));
        users.add(new JWTUser(
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
    public static JWTUser getUser(String username) {
        List<JWTUser> users = SystemUtils.users();
        return users.stream().filter(user -> StringUtils.equalsIgnoreCase(username, user.getUsername())).findFirst().orElse(null);
    }
}
