package com.shenlx.xinwen.spring.security.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-27 09:46
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * SpringSecurity是针对Spring项目的安全框架，也是SpringBoot底层安全模块默认的技术选型，他可以实现强大的Web安全控制，对于安全控制，我们仅需要引入spring-boot-starter-security模块，进行少量的配置，即可实现强大的安全管理功能。
     *
     * SpringSecurity中几个重要的类：
     *
     * WebSecurityConfigurerAdapter：自定义Security安全策略
     *
     * AuthenticationManagerBuilder：自定义认证策略
     *
     * @EnableWebSecurity：开启WebSecurity模式
     *
     * 类WebSecurityConfigurerAdapter中重要的方法：
     */
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers(&quot;/**&quot;).hasRole(&quot;USER&quot;).and().formLogin()
//         .usernameParameter(&quot;username&quot;) // default is username
//         .passwordParameter(&quot;password&quot;) // default is password
//         .loginPage(&quot;/authentication/login&quot;) // default is /login with an HTTP get
//         .failureUrl(&quot;/authentication/login?failed&quot;) // default is /login?error
//         .loginProcessingUrl(&quot;/authentication/login/process&quot;); // default is /login
//         // with an HTTP
//         // post
//         }

    /**
     * SpringSecurity的两个主要目标是 “认证” 和 “授权”（访问控制）
     *
     * “认证”（Authentication）
     *
     * 身份验证是关于验证身份的凭据，如用户名、用户ID和密码，以验证身份权限，身份验证通常通过用户名和密码完成，有时与身份验证因素结合使用
     *
     * “授权” （Authorization）
     *
     * 授权发生在系统成功验证用户的身份后，最终会授予用户访问资源（如信息，文件，数据库，资金，位置，几乎任何内容）的完全权限
     */

    /**
     * •在为某个请求提供服务之前，需要预先满足特定的条件；
     * •配置自定义的登录页；
     * •支持用户退出应用；
     * •预防跨站请求伪造。
     * 配置HttpSecurity常见的需求就是拦截请求以确保用户具备适当的权限
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置不需要登陆验证
//        http.authorizeRequests().anyRequest().permitAll().and().logout().permitAll()
//                //关闭防止跨站请求伪造请求
//            .and().csrf().disable();

        http.formLogin() // 表单登录
                // http.httpBasic() // HTTP Basic
                .and()
                .authorizeRequests() // 授权配置
                .anyRequest()  // 所有请求
                .authenticated(); // 都需要认证
    }
}
