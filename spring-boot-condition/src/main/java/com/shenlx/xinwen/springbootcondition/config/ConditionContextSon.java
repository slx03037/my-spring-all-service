package com.shenlx.xinwen.springbootcondition.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

public interface ConditionContextSon extends ConditionContext {
    /**
     * 返回bean定义注册器，可以通过注册器获取bean定义的各种配置信息
     */
    BeanDefinitionRegistry getRegistry();
    /**
     * 返回ConfigurableListableBeanFactory类型的bean工厂，相当于一个ioc容器对象
     */
    @Nullable
    ConfigurableListableBeanFactory getBeanFactory();
    /**
     * 返回当前spring容器的环境配置信息对象
     */
    Environment getEnvironment();
    /**
     * 返回资源加载器
     */
    ResourceLoader getResourceLoader();
    /**
     * 返回类加载器
     */
    @Nullable
    ClassLoader getClassLoader();
}
