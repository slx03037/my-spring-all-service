package com.shenlx.xinwen.springbootelasticsearchrestclient.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-06 18:23
 */

/**
 * 注入bean
 */
@Configuration
@Slf4j
//@EnableAutoConfiguration(exclude = ElasticSearchProperties.class)
public class ElasticsearchClientConfig {
    @Resource
    private ElasticSearchProperties elasticSearchProperties;
    @Bean
    public ElasticsearchClient restHighLevelClient() {
        log.info("elastic的ip：{}，port:{}",elasticSearchProperties.getHost(),elasticSearchProperties.getPort());
        RestClient restClient = RestClient.builder(
                new HttpHost(elasticSearchProperties.getHost(), elasticSearchProperties.getPort())
        ).build();
        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(elasticsearchTransport);
    }

    /**
     * 异步连接客户端
     */
    @Bean
    public ElasticsearchAsyncClient restElasticsearchClientAsync() {
        RestClient restClient = RestClient.builder(
                new HttpHost(elasticSearchProperties.getHost(), elasticSearchProperties.getPort())
        ).build();
        ElasticsearchTransport elasticsearchTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        return new ElasticsearchAsyncClient(elasticsearchTransport);
    }
    /**
     * 注入elasticsearch的bean  8.0之后废弃 配置jdk为8
     * @return
     */
//    @Bean
//    public RestHighLevelClient restHighLevelClient() {
//        HttpHost httpHost=new HttpHost(host, port, "http");
//        /**
//         * 集群的方式
//         */
////        HttpHost[] httpHosts =
////                Arrays.stream(hosts.split(","))
////                .filter(e -> !StringUtils.isEmpty(e))
////                .map(e -> )
////                .toArray(HttpHost[]::new);
//
//        return new RestHighLevelClient(
//                RestClient.builder(
//                        httpHost
//                )
//        );
//    }
}
