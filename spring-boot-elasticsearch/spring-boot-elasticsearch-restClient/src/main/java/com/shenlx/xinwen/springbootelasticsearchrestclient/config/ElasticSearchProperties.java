package com.shenlx.xinwen.springbootelasticsearchrestclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-06 17:56
 */
@Data
@Component
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {
    private String host;
    private Integer port;
}
