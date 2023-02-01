package com.slx.springboot.security_oauth2.customize.controller;

import com.slx.springboot.security.smscode.validate.SmsCode;
import com.slx.springboot.security_oauth2.customize.service.RedisCodeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-01 15:20
 **/

@RestController
public class ValidateController {

    @Autowired
    private RedisCodeService redisCodeService;

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response, String mobile) throws Exception {
        SmsCode smsCode = createSMSCode();
        redisCodeService.save(smsCode, new ServletWebRequest(request), mobile);
        // 输出验证码到控制台代替短信发送服务
        System.out.println("手机号" + mobile + "的登录验证码为：" + smsCode.getCode() + "，有效时间为120秒");
    }

    private SmsCode createSMSCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new SmsCode(code);
    }

}
