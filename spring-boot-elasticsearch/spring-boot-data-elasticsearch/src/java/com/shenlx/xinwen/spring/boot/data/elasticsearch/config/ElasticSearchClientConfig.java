package com.shenlx.xinwen.spring.boot.data.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-10 16:22
 */
@Configuration
public class ElasticSearchClientConfig {
    @Resource
    private ElasticProperties elasticProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticProperties.getHost(), elasticProperties.getPort(), "http")));
        return client;
    }
}
