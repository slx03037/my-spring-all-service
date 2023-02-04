package com.shenlx.xinwen.springbootresttemplate.controller;

import com.shenlx.xinwen.springbootresttemplate.model.BaseResponse;
import com.shenlx.xinwen.springbootresttemplate.model.LoginParam;
import com.shenlx.xinwen.springbootresttemplate.model.UnbindParam;
import org.apache.http.Header;
import org.eclipse.jetty.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-02 21:45
 **/

@RestController
public class LoginController {

    private static final Logger _logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestHeader(name = "Content-Type", defaultValue = "application/json") String contentType,
                                      @RequestBody LoginParam loginParam) {
        _logger.info("用户请求登录获取Token：{},{}",contentType, loginParam);
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        return new BaseResponse<>(true, "Login success", username + password);
    }

    @PostMapping("/unbind")
    public BaseResponse<String> unbind(@RequestHeader(name = "Content-Type", defaultValue = "application/json") String contentType,
                                       @RequestHeader(name = "Authorization", defaultValue = "token") String token,
                                       @RequestBody UnbindParam unbindParam,
                                       HttpServletRequest request
    ) {
        _logger.info("解绑通知接口start,{},{},{},{}",contentType,token,unbindParam,JSONObject.wrap(request.getAttribute("id")) );
        String imei = unbindParam.getImei();
        String location = unbindParam.getLocation();
        return new BaseResponse<>(true, "解绑通知发送成功", "unbind");
    }

}