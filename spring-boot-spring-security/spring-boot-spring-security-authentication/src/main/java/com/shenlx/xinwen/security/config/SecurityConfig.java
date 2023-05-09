package com.shenlx.xinwen.security.config;

import com.shenlx.xinwen.security.handler.MyAuthenticationFailureHandler;
import com.shenlx.xinwen.security.handler.MyAuthenticationSucessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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

    @Autowired
    private DataSource dataSource;

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

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);

        return jdbcTokenRepository;
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
                .rememberMe()
                /**
                 * 记住我
                 * 如果要实现登录时记住密码的功能，登录之后，关闭浏览器，再次登录不需要输入用户名密码便可以直接登录成功，需要开启记住我功能。
                 *
                 * **原理：**登录成功后，将cookie发送给浏览器保存，再次登录会带上该cookie信息，通过检查便可以免登录了；如果点击注销，则会删除该cookie信息
                 *
                 *
                 * //定制请求的授权规则
                 */
               .tokenRepository(persistentTokenRepository()) // 配置 token 持久化仓库
                .tokenValiditySeconds(3600) // remember 过期时间，单为秒
                //.userDetailsService(UserDetailService) // 处理自动登录逻辑
                .and()
                .authorizeRequests() // 授权配置
                .antMatchers("/authentication/require", "/login.html").permitAll() // 登录跳转 URL 无需认证
                .anyRequest()  // 所有请求
                .authenticated() // 都需要认证
                .and().csrf().disable();
    }
}
