package com.shenlx.xinwen.jwt.cache.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-04 14:59
 **/

public class Role implements Serializable {
    private static final long serialVersionUID = -8532986679882439315L;
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

    /**
     * 获取 主键ID.
     *
     * @return 主键ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 主键ID.
     *
     * @param id 主键ID.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 角色名称.
     *
     * @return 角色名称.
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置 角色名称.
     *
     * @param role 角色名称.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取 角色说明.
     *
     * @return 角色说明.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 角色说明.
     *
     * @param description 角色说明.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取 创建时间.
     *
     * @return 创建时间.
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置 创建时间.
     *
     * @param createdTime 创建时间.
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取 更新时间.
     *
     * @return 更新时间.
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置 更新时间.
     *
     * @param updatedTime 更新时间.
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    protected Serializable pkVal() {
        return this.id;
    }

}
