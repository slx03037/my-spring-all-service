package com.shenlx.xinwen.report.controller;

import com.shenlx.xinwen.report.service.ApachePoitlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-07-12 23:16
 **/

@RestController
@RequestMapping("/poitl")
public class ApachePoiTlController {

    @Autowired
    private ApachePoitlService poitlService;

    @GetMapping("/generateCharts")
    public void generateCharts(){
        poitlService.generateCharts();
    }
}
