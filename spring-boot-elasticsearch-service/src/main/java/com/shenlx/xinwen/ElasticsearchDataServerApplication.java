package com.shenlx.xinwen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @program: my-spring-all-service
 * @description: elasticsearch搜索引擎的启动类
 * @author: shenlx
 * @create: 2022-12-28 19:26
 **/
@SpringBootApplication(scanBasePackages = "com.shenlx.xinwen")
@MapperScan({"com.shenlx.xinwen.mapper"})
@EnableElasticsearchRepositories(basePackages = "com.shenlx.xinwen.repository")
public class ElasticsearchDataServerApplication {
    public static void main(String[] args){
        SpringApplication.run(ElasticsearchDataServerApplication.class,args);
    }
}
