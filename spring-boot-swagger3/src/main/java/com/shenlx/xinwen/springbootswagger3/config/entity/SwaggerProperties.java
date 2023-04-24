package com.shenlx.xinwen.springbootswagger3.config.entity;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-24 15:36
 **/

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * swagger的属性配置类
 */
@ConfigurationProperties(prefix = "spring.swagger")
@Data
public class SwaggerProperties {
    /**
     * 前台接口配置
     */
    private SwaggerEntity front;

    /**
     * 后台接口配置
     */
    private SwaggerEntity back;

//    // 配置文档信息
//    public ApiInfo apiInfo() {
//        // contact 联系人信息
//        Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
//        return new ApiInfo(
//                "Swagger学习", // 标题
//                "学习演示如何配置Swagger", // 描述
//                "v1.0", // 版本
//                "http://terms.service.url/组织链接", // 组织链接
//                contact, // 联系人信息
//                "Apach 2.0 许可", // 许可
//                "许可链接", // 许可连接
//                new ArrayList<>() // 扩展
//        );
//    }

    @Data
    public static class SwaggerEntity {
        private String groupName;
        private String basePackage;
        private String title; //标题
        private String description; //描述
        private String contactName;// 联系人姓名
        private String contactEmail; // 联系人邮箱
        private String contactUrl;  // 组织链接
        private String version; // 版本
        private Boolean enable;
    }

}
