package com.shenlx.xinwen.springbootconfig.controller;

import com.shenlx.xinwen.springbootconfig.bean.PrefixPropertiesArrayBeans;
import com.shenlx.xinwen.springbootconfig.bean.PropertiesGetBean;
import com.shenlx.xinwen.springbootconfig.bean.TestConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-10 11:27
 **/
@RestController
public class TestController {
    @Resource
    private PropertiesGetBean propertiesGetBean;

    @Resource
    private PrefixPropertiesArrayBeans prefixPropertiesArrayBeans;

    @Resource
    private TestConfigBean testConfigBean;

    @GetMapping("/get")
    public String get(){
        return String.format("端口号:%s",propertiesGetBean.getPort());
    }

    @GetMapping("/getObj")
    public String getObj(){
        return String.format("obj:%s",prefixPropertiesArrayBeans.toString());
    }

    @GetMapping("/getTest")
    public String getTest(){
        return String.format("指定文件路径:%s",testConfigBean.toString());
    }
}
