package com.shenlx.xinwen.springbootresttemplatepush.controller;

import com.shenlx.xinwen.springbootresttemplatepush.service.MessageGetService;
import com.shenlx.xinwen.springbootresttemplatepush.service.MessagePostService;
import com.shenlx.xinwen.springbootresttemplatepush.util.PushApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-10 12:35
 **/
@RestController
public class MessagePushController {
    @Resource
    private MessageGetService messageGetService;
    @Resource
    private MessagePostService messagePostService;
    @Resource
    PushApplicationContext pushApplicationContext;
    @GetMapping("/push")
    public String pushString(){
       return messageGetService.getString();
    }

    @GetMapping("/pushPost")
    public String pushPostString(){
        return messagePostService.postString();
    }

    @GetMapping("pushUrl")
    public String getPushUrl(){
        return pushApplicationContext.getAuthCenter();
    }
}
