package com.shenlx.xinwen.springbootfilter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-11 21:55
 **/
@Component
@Slf4j
public class Time2Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("开始执行过滤器");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //继续执行下一个过滤器
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {

    }
}
