package com.shenlx.xinwen.webflux.config;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenlx
 * @description
 * @date 2024/4/18 11:31
 */

@Configuration
public class WebConfig {

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        // 可以选择不同的服务器，例如Netty, Undertow或者Jetty
        return new NettyReactiveWebServerFactory();
    }
}