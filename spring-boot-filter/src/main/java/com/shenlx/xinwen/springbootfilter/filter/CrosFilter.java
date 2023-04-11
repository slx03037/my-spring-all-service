package com.shenlx.xinwen.springbootfilter.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * 使用@WebFilter
 * @WebFilter 是Servlet3.0的一个注解，用于标注一个Filter，Spring Boot也是支持这种方式，只需要在
 * 自定义的Filter上标注该注解即可
 * @author: shenlx
 * @create: 2023-04-11 22:05
 **/

@WebFilter(filterName = "crosFilter",urlPatterns = {"/*"})
public class CrosFilter implements Filter {
    //重写其中的doFilter方法
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        //继续执行下一个过滤器
        chain.doFilter(request, response);
    }
}

