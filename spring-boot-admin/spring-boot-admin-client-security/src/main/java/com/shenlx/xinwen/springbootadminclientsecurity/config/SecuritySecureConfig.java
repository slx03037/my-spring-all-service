package com.shenlx.xinwen.springbootadminclientsecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @program: my-spring-all-service
 * @description: Security安全配置
 * @author: shenlx
 * @create: 2023-03-21 16:10
 **/
@Configuration
@Slf4j
public  class SecuritySecureConfig  {
 //   private final String adminContextPath;

//    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
//        this.adminContextPath = adminServerProperties.getContextPath();
//    }
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll())
                .csrf().disable().build();

//        SavedRequestAwareAuthenticationSuccessHandler successHandler=new SavedRequestAwareAuthenticationSuccessHandler();
//        successHandler.setTargetUrlParameter("redirectTo");
//        //successHandler.setDefaultTargetUrl(adminContextPath+"/");
//        http.authorizeRequests()
//                //无需登录即可访问
//                .antMatchers(adminContextPath + "/assets/**").permitAll()
//                .antMatchers(adminContextPath + "login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                //登录和登录路径
//                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler)
//                .and()
//                .logout().logoutUrl(adminContextPath + "/logout")
//                .and()
//                //开启http basic支持，admin-client注册时需要使用
//                .httpBasic()
//                .and()
//                .csrf()
//                .disable();
//                //开启基于cookie的csrf保护
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                //忽略这些路径的csrf保护一边admin-client注册
//                .ignoringAntMatchers(
//                        adminContextPath+"instances",
//                        adminContextPath+"/actuator/**"
//                );

    }

}
