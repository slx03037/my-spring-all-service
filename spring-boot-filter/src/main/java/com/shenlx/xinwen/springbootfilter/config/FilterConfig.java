package com.shenlx.xinwen.springbootfilter.config;

import com.shenlx.xinwen.springbootfilter.filter.Time2Filter;
import com.shenlx.xinwen.springbootfilter.filter.TimeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-11 21:55
 **/
@Configuration
public class FilterConfig {
    @Autowired
    private TimeFilter filter1;

    @Autowired
    private Time2Filter filter2;

    /**
     * 注入Filter1
     * @return
     */
    @Bean
    public FilterRegistrationBean filter1() {
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(filter1);
        registration.addUrlPatterns("/*");
        registration.setName("filter1");
        //设置优先级别
        registration.setOrder(1);
        return registration;
    }

    /**
     * 注入Filter2
     * @return
     */
    @Bean
    public FilterRegistrationBean filter2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter2);
        registration.addUrlPatterns("/*");
        registration.setName("filter2");
        //设置优先级别
        registration.setOrder(2);
        return registration;
    }

}
