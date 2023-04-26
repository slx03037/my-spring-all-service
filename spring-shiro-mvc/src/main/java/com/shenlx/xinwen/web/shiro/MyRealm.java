package com.shenlx.xinwen.web.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-25 10:26
 **/

public class MyRealm extends AuthorizingRealm {
    //    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object principal = principals.getPrimaryPrincipal();
        System.out.println("RealmForDouble授权中---->用户："+principal);
        SimpleAuthorizationInfo info = null;
        Set<String> roles = new HashSet<>();
        if ("admin".equals(principal)){
            roles.add("admin");
        }
        if ("guest".equals(principal)){
            roles.add("guest");
        }
        info = new SimpleAuthorizationInfo(roles);
        return info;
    }

    //    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("RealmForDouble认证中---->用户："+token.getPrincipal());
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //String password="123456";// 假设这是从数据库中查询到的用户密码

        String password="e10adc3949ba59abbe56e057f20f883e";// md5(123456)
        String salt = "lilei";//假设这个盐值是从数据库中查出的
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(upToken.getUsername(),password,this.getName());
        return info;
    }
}
