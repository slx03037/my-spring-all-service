package com.shenlx.xinwen.springbootconfig.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 11:21
 **/
@ConfigurationProperties(prefix="com.shenlx")
@Component
@ToString
@Data
public class PrefixPropertiesArrayBeans {
        private String groupId;

        private List<String> service;
}
