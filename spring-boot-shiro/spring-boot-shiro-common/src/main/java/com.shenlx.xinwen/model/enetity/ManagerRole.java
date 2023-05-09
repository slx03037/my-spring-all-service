package com.shenlx.xinwen.model.enetity;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-08 14:49
 **/

public class ManagerRole implements Serializable {
    private static final long serialVersionUID = 6111174309165501149L;
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
     * 获取 管理用户ID.
     *
     * @return 管理用户ID.
     */
    public Integer getManagerId() {
        return managerId;
    }

    /**
     * 设置 管理用户ID.
     *
     * @param managerId 管理用户ID.
     */
    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    /**
     * 获取 角色ID.
     *
     * @return 角色ID.
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置 角色ID.
     *
     * @param roleId 角色ID.
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
