package com.shenlx.xinwen.spring.boot.data.elasticsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-10 16:25
 */
@ConfigurationProperties(prefix="elasticsearch")
@Component
@Data
public class ElasticProperties {
    private String host;
    private Integer port;
}
