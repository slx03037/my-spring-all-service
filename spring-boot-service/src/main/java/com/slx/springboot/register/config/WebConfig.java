package com.slx.springboot.register.config;

import com.slx.springboot.register.domian.Hello;
import com.slx.springboot.register.domian.UserRegisterDO;
import com.slx.springboot.register.factory.CherryFactoryBean;
import com.slx.springboot.register.register.MyImportBeanDefinitionRegistrar;
import com.slx.springboot.register.selector.MyImportSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 22:11
 **/
@Configuration
// @ComponentScan(value = "cc.mrbird.demo"
// , excludeFilters = {
//         @Filter(type = FilterType.ANNOTATION,
//                 classes = {Controller.class, Repository.class}),
//         @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = User.class)
//         @Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)
// }
// includeFilters = {
//         @Filter(type = FilterType.ANNOTATION, classes = Service.class)
// }, useDefaultFilters = false
// )
@Import({Hello.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class WebConfig {
    @Bean
    // @Conditional(MyCondition.class)
    // @Lazy
    // @Scope("prototype")
    public UserRegisterDO user() {
        System.out.println("往IOC容器中注册user bean");
        return new UserRegisterDO("mrbird", 18);
    }

    @Bean
    public CherryFactoryBean cherryFactoryBean() {
        return new CherryFactoryBean();
    }
}
