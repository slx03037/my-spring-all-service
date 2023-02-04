package com.shenlx.xinwen.springbootresttemplate.model;

import java.io.Serializable;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 21:44
 **/

public class LoginParam implements Serializable {
    private static final long serialVersionUID = -3241221362408437652L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * Application ID
     */
    private String appid;
    /**
     * IMEI码
     */
    private String imei;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}

