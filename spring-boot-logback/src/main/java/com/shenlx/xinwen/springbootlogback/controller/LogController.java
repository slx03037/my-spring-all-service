package com.shenlx.xinwen.springbootlogback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-03-29 21:48
 **/
@RestController
@Slf4j
public class LogController {
    @GetMapping("/test")
    public String test(){
        return "success";
    }

    @GetMapping("/error")
    public String error(){
        try{
            errorMethod();
        }catch (Exception e){
            log.error("失败拉----------拉：{}",e.getMessage());
        }
        return "error";
    }

    private void errorMethod(){
        int a=0/1;
    }

}
