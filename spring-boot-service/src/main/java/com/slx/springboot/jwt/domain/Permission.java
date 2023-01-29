package com.slx.springboot.jwt.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-28 23:22
 **/
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 4464154699925205837L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 权限名称
     */
    private String permission;
    /**
     * 权限说明
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}
