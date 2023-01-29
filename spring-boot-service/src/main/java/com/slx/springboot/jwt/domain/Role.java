package com.slx.springboot.jwt.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-28 23:23
 **/
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 5306880227807024116L;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String role;
    /**
     * 角色说明
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
