package com.shenlx.xinwen.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author shenlx
 * @description
 * @date 2024/4/18 11:08
 */
//@EnableWebFlux
@SpringBootApplication
public class SpringWebfluxTestApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringWebfluxTestApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }
}
