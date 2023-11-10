package com.shenlx.xinwen.springbootelasticsearchrestclient;

import com.shenlx.xinwen.springbootelasticsearchrestclient.config.ElasticSearchProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootElasticsearchRestClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootElasticsearchRestClientApplication.class, args);
    }

}
