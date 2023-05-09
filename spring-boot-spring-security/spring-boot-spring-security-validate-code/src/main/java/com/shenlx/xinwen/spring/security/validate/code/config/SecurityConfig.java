package com.shenlx.xinwen.spring.security.validate.code.config;

import com.shenlx.xinwen.spring.security.validate.code.handler.MyAuthenticationFailureHandler;
import com.shenlx.xinwen.spring.security.validate.code.handler.MyAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;

//import javax.sql.DataSource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-27 14:52
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;


//    @Autowired
//    private UserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        /**
         * passwordEncoder()方法可以接受Spring Security中PasswordEncoder接口的任意实现。
         * Spring Security的加密模块包括了多个这样的实现。
         * •BCryptPasswordEncoder：使用bcrypt强哈希加密。
         * •NoOpPasswordEncoder：不进行任何转码。
         * •Pbkdf2PasswordEncoder：使用PBKDF2加密。
         * •SCryptPasswordEncoder：使用scrypt哈希加密。
         * •StandardPasswordEncoder：使用SHA-256哈希加密
         */
       // return new NoOpPasswordEncoder();
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 表单登录
                // http.httpBasic() // HTTP Basic
                .loginPage("/authentication/require") // 登录跳转 URL
                .loginProcessingUrl("/login") // 处理表单登录 URL
                .successHandler(authenticationSucessHandler) // 处理登录成功
                .failureHandler(authenticationFailureHandler) // 处理登录失败
                .and()
                .authorizeRequests() // 授权配置
                .antMatchers("/authentication/require", "/login.html").permitAll() // 登录跳转 URL 无需认证
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
                .and().csrf().disable();
    }
}
