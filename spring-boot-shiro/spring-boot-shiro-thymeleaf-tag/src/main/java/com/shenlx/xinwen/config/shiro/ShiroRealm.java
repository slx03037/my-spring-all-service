package com.shenlx.xinwen.config.shiro;

import com.shenlx.xinwen.dao.UserMapper;
import com.shenlx.xinwen.dao.UserPermissionMapper;
import com.shenlx.xinwen.dao.UserRoleMapper;
import com.shenlx.xinwen.domain.Permission;
import com.shenlx.xinwen.domain.Role;
import com.shenlx.xinwen.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-26 15:28
 **/
@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper userPermissionMapper;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行了=>授权doGetAuthorizationInfo");
        // 获取当前登录用户
//        Subject subject = SecurityUtils.getSubject();
//        subject.getPrincipal();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUsername();

        log.info("用户 :{}--获取权限-----ShiroRealm.doGetAuthorizationInfo",userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集 可以调用数据库接口  获取认证方法中查到的当前登录用户信息 : User 对象
        List<Role> roleList = userRoleMapper.findByUserName(userName);
       // List<Role> roleList = new ArrayList<>();
       // Role role1=new Role(1,"admin","system");
//        Role role2=new Role(2,"test","test");
//        Role role3=new Role(3,"common","fw");
       // roleList.add(role1);
//        roleList.add(role2);
//        roleList.add(role3);

        Set<String> roleSet = new HashSet<String>();
        for (Role r : roleList) {
            roleSet.add(r.getName());
        }
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集 可以调用数据库接口  获取认证方法中查到的当前登录用户信息 : User 对象
        List<Permission> permissions = userPermissionMapper.findByUserName(userName);

//        List<Permission> permissions = new ArrayList<>();
//        Permission permission1=new Permission(1,"/add","user:add");
//        Permission permission2=new Permission(2,"/list","user:list");
//        Permission permission3=new Permission(3,"/update","user:update");
//        permissions.add(permission1);
//        permissions.add(permission2);
//        permissions.add(permission3);

        Set<String> permissionSet = new HashSet<String>();

        for (Permission p : permissions) {
            permissionSet.add(p.getName());
        }
        // 获取当前用户在数据库中查询到的拥有的权限, 并为当前用户设置该权限
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("执行了=>认证doGetAuthenticationInfo");
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        log.info("用户:{},密码：{}--ShiroRealm.doGetAuthenticationInfo",userName,password);
        /**
         * 此处的User 是根据用户名  在数据库查询出来的或者从redis中取出来的， 进行对比
         */

        User user = userMapper.findByUserName(userName);
        log.info("认证用户user:{}",user);
       // User user =new User();
//        user.setUsername("admin");
//        user.setPassword("5db06fa6532fde63688f2e21a9c1a9d6");
//        user.setStatus("1");
        // 验证用户名密码状态是否输入正确
        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        // 验证密码是否输入正确, 由 Shiro 自动完成
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}

