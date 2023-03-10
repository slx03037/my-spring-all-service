package com.shenlx.xinwen.springbootresttemplateget.controller;

import com.shenlx.xinwen.common.base.CommonResult;
import com.shenlx.xinwen.springbootresttemplateget.entity.UserDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-10 11:26
 **/
@RestController
public class MessageGetController {
    private static final Logger log= LoggerFactory.getLogger(MessageGetController.class);

    @GetMapping("/get")
    public CommonResult<Void> getString(){
        return CommonResult.success();
    }

    @PostMapping("/post")
    public CommonResult<UserDo> post(@RequestBody UserDo userDo){
        log.info("请求结果：{}",userDo);
        UserDo userDo1 = new UserDo();
        userDo1.setAge(10);
        userDo1.setName("里斯");
        userDo1.setSex((byte) 20);
        CommonResult<UserDo> objectCommonResult = new CommonResult<>();
        objectCommonResult.setData(userDo1);
        log.info("返回参数：{}",objectCommonResult);
        return objectCommonResult;
    }

}
