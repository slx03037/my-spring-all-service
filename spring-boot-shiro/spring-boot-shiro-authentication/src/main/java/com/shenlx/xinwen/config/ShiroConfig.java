package com.shenlx.xinwen.config;

import com.shenlx.xinwen.config.shiro.ShiroRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-26 09:21
 **/

@Configuration
public class ShiroConfig {

    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置拦截登录的请求(拦截之后跳转的页面)
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // (1)设置拦截器链 : anon authc logout roles perms
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    // DefaultWebSecurityManager
    //以上代码为Shiro配置类的固定写法，需要创建ShiroFilterFactoryBean过滤器对象、DefaultWebSecurityManager对象、自定义Realm对象
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        // 关联 ShiroRealm
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

   // 2.通过@Qualifier标签拿到userRealm()方法创建的Bean对象，绑定到getDefaultWebSecurityManager()方法中的参数shiroRealm上
//    @Bean
//    public DefaultWebSecurityManager securityManager(@Qualifier("shiroRealm")ShiroRealm shiroRealm){
//        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//        // 关联 ShiroRealm
//        securityManager.setRealm(shiroRealm);
//        return securityManager;
//    }

    // 创建 realm 对象, 需要自定义类
    // @Bean(name = "ShiroRealm")
    @Bean
    public ShiroRealm shiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        return shiroRealm;
    }
}
