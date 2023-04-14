package com.shenlx.xinwen.springbootcondition.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-12 16:38
 **/

public class WindowsCondition implements Condition {

    /**
     * context ：条件上下文， ConditionContext 接口类型的，可以用来获取容器中上下文信息。
     * metadata ：用来获取被 @Conditional 标注的对象上的所有注解信息
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取当前环境信息
        Environment environment = context.getEnvironment();
        //获得当前系统名
        String property = environment.getProperty("os.name");
        //包含Windows则说明是windows系统，返回true
        if (property.contains("Windows")){
            return true;
        }
        return false;
    }
}
