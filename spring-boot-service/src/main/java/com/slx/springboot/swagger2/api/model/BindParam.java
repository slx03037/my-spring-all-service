package com.slx.springboot.swagger2.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: my-spring-all-service
 * @description: POS绑定网点参数
 * @author: shenlx
 * @create: 2023-01-10 22:08
 **/

@ApiModel(value = "POS绑定网点参数", description = "POSPOS绑定网点请求时候需要传入的参数")
public class BindParam {
    /**
     * 机具IMEI码
     */
    @ApiModelProperty(value = "IMEI码（长度1-32位）", name = "imei", dataType = "String", example = "2324DEEFAXX122", required = true)
    private String imei;
    /**
     * 归属网点
     */
    @ApiModelProperty(value = "归属网点（长度1-64位）", name = "location", dataType = "String", example = "昆明市公安局交通警察支队车辆管理所", required = true)
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
