package com.slx.spring.config;

import com.slx.spring.handler.SocketHandler;
import com.slx.spring.interceptor.WebSocketInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 20:59
 **/
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/app")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
