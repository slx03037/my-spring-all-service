package com.shenlx.xinwen.jwt.cache.model;

import com.shenlx.xinwen.jwt.cache.domain.Manager;

import java.util.List;

/**
 * @program: my-spring-all-service
 * @description: 后台运维管理员信息
 * @author: shenlx
 * @create: 2023-05-04 14:56
 **/

public class ManagerInfo extends Manager {


    private static final long serialVersionUID = -8069351278559667353L;
    /**
     * 状态
     */
    private String stateStr;
    /**
     * 所属项目id列表（逗号分隔）
     */
    private String pids;
    /**
     * 所属项目名列表（逗号分隔）
     */
    private String pnames;
    /**
     * 所属项目id列表
     */
    private List<Integer> pidsList;

    /**
     * 一个管理员具有多个角色
     */
    private List<SysRole> roles;// 一个用户具有多个角色

    public ManagerInfo() {
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    /**
     * 密码盐
     */
    public String getCredentialsSalt() {
        return getUsername() + getSalt();
    }

    @Override
    public String toString() {
        return "username:" + getUsername() + "|name=" + getName();
    }

    public String getStateStr() {
        return stateStr;
    }

    public void setStateStr(String stateStr) {
        this.stateStr = stateStr;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public List<Integer> getPidsList() {
        return pidsList;
    }

    public void setPidsList(List<Integer> pidsList) {
        this.pidsList = pidsList;
    }

    public String getPnames() {
        return pnames;
    }

    public void setPnames(String pnames) {
        this.pnames = pnames;
    }

}