package com.shenlx.xinwen.minio.config;

import com.shenlx.xinwen.minio.properties.MinioPropertiesConfig;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-21 17:05
 **/
@Configuration
@EnableConfigurationProperties(MinioPropertiesConfig.class)
public class MinIOConfig {
    @Resource
    private MinioPropertiesConfig minioPropertiesConfig;


    /**
     * 初始化 MinIO 客户端
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioPropertiesConfig.getEndpoint())
                .credentials(minioPropertiesConfig.getAccessKey(), minioPropertiesConfig.getSecretKey())
                .build();
        return minioClient;
    }

}
