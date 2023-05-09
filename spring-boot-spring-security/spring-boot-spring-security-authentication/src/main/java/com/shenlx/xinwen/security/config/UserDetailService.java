package com.shenlx.xinwen.security.config;

import com.shenlx.xinwen.common.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-27 14:51
 **/
@Configuration
public class UserDetailService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟一个用户，替代数据库获取逻辑
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setPassword(this.passwordEncoder.encode("123456"));
        // 输出加密后的密码
        System.out.println(userDO.getPassword());

        return new User(username, userDO.getPassword(), userDO.isEnabled(),
                userDO.isAccountNonExpired(), userDO.isCredentialsNonExpired(),
                userDO.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }
}
