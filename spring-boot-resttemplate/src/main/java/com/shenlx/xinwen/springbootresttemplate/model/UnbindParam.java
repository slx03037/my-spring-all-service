package com.shenlx.xinwen.springbootresttemplate.model;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 21:45
 **/

public class UnbindParam {
    /**
     * IMEI码
     */
    private String imei;
    /**
     * 网点
     */
    private String location;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

