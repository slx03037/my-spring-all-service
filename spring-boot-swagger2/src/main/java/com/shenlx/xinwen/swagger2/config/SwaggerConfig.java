package com.shenlx.xinwen.swagger2.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-21 11:11
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Springfox-swagger 的配置通过一个 Docket 来包装，Docket 里的 apiInfo 方法可以传入关于接口总体的描述信息。而 apis 方法可以指定要扫描的包的具体路径。
     * 在类上添加 @Configuration 声明这是一个配置类，最后使用 @EnableSwagger2 开启 Springfox-swagger2。
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.shenlx.xinwen.swagger2.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SWAGGER_2")
                .description("SWAGGER_2_FIRST_LEARN")
                .termsOfServiceUrl("http://localhost:8080")
                .contact("NONE LERANING")
                .version("1.0")
                .build();
    }
}
