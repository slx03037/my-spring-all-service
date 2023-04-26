package com.shenlx.xinwen.config.shiro;

import com.shenlx.xinwen.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-26 09:25
 **/
@Slf4j
public class ShiroRealm extends AuthorizingRealm {
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=>授权doGetAuthorizationInfo");
        return null;
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
        User user =new User();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setStatus("1");
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
