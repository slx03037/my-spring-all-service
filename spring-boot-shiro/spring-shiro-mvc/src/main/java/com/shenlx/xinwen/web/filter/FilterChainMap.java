package com.shenlx.xinwen.web.filter;

import java.util.LinkedHashMap;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-25 10:34
 **/

public class FilterChainMap {
    // 使用静态工厂
    public static LinkedHashMap<String, String> getFilterChainMap(){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 下面的数据可以从数据库中查询出来。
        map.put("/login.jsp", "anon");
        map.put("/shiro/login", "anon");
        map.put("/shiro/logout", "logout");
        map.put("/admin.jsp", "authc");
        map.put("/**", "authc");
        return map;
    }
}
