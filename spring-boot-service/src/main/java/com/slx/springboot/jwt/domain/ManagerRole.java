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
public class ManagerRole implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * 管理用户ID
     */
    private Integer managerId;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
}
