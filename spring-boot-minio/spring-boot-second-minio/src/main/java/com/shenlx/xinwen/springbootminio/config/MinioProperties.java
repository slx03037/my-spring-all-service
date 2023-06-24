package com.shenlx.xinwen.springbootminio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-23 16:00
 **/
@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProperties {
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 存储桶bucket名称
     */
    private String bucketName;
    /**
     * 文件地址
     */
    private String fileHost;
}
