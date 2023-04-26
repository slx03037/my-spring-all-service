package com.shenlx.xinwen.service;

import org.apache.shiro.authz.annotation.RequiresRoles;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-25 10:48
 **/

/**
 * 注解式就是使用注解来进行权限管理。【这些注解不能用在controller中】
 * 除了·@RequiresRoles("admin")，常见的注解还有：
 * @RequiresPermissions():是否拥有某个权限
 * 注意，注解的使用需要以下两个bean:
 */
public class UserService {
    /**
     * shiro标签的标签可以查看readme.md 文档
     */
    @RequiresRoles("admin") // 需要角色admin
    public void deluser(){
        System.out.println("执行了删除用户的操作");
    }
}
