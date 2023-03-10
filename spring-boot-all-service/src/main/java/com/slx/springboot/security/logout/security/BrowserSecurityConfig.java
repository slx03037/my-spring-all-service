package com.slx.springboot.security.logout.security;

import com.slx.springboot.common.handler.MyAuthenticationFailureHandler;
import com.slx.springboot.common.handler.MyAuthenticationSucessHandler;
import com.slx.springboot.security.logout.handler.MyLogOutSuccessHandler;
import com.slx.springboot.security.sessionmanager.session.MySessionExpiredStrategy;
import com.slx.springboot.security.smscode.validate.SmsAuthenticationConfig;
import com.slx.springboot.security.smscode.validate.SmsCodeFilter;
import com.slx.springboot.security.validatecode.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 10:57
 **/

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationSucessHandler authenticationSucessHandler;

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private SmsCodeFilter smsCodeFilter;

    @Autowired
    private SmsAuthenticationConfig smsAuthenticationConfig;
    @Autowired
    private MySessionExpiredStrategy sessionExpiredStrategy;

    @Autowired
    private MyLogOutSuccessHandler logOutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // ??????????????????????????????
                .addFilterBefore(smsCodeFilter,UsernamePasswordAuthenticationFilter.class) // ????????????????????????????????????
                .formLogin() // ????????????
                // http.httpBasic() // HTTP Basic
                .loginPage("/authentication/require") // ???????????? URL
                .loginProcessingUrl("/login") // ?????????????????? URL
                .successHandler(authenticationSucessHandler) // ??????????????????
                .failureHandler(authenticationFailureHandler) // ??????????????????
                .and()
                .authorizeRequests() // ????????????
                .antMatchers("/authentication/require",
                        "/login.html", "/code/image","/code/sms","/session/invalid", "/signout/success").permitAll() // ???????????????????????????
                .anyRequest()  // ????????????
                .authenticated() // ???????????????
                .and()
                .sessionManagement() // ?????? Session?????????
                .invalidSessionUrl("/session/invalid") // Session??????????????????????????????
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(sessionExpiredStrategy)
                .and()
                .and()
                .logout()
                .logoutUrl("/signout")
                // .logoutSuccessUrl("/signout/success")
                .logoutSuccessHandler(logOutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable()
                .apply(smsAuthenticationConfig); // ???????????????????????????????????? Spring Security ???
    }
}

