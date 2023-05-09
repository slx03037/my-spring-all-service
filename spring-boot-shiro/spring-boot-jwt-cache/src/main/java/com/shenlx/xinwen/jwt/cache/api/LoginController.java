package com.shenlx.xinwen.jwt.cache.api;

import com.shenlx.xinwen.jwt.cache.config.jwt.JWTUtil;
import com.shenlx.xinwen.jwt.cache.config.shiro.ShiroKit;
import com.shenlx.xinwen.jwt.cache.exception.UnauthorizedException;
import com.shenlx.xinwen.jwt.cache.model.BaseResponse;
import com.shenlx.xinwen.jwt.cache.model.LoginParam;
import com.shenlx.xinwen.jwt.cache.model.ManagerInfo;
import com.shenlx.xinwen.jwt.cache.service.ManagerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-04 15:12
 **/
@Slf4j
@RestController
public class LoginController {
    @Resource
    private ManagerInfoService managerInfoService;


    @PostMapping("/login")
    public BaseResponse<String> login(@RequestHeader(name="Content-Type", defaultValue = "application/json") String contentType,
                                      @RequestBody LoginParam loginParam) {
        log.info("用户请求登录获取Token");
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        ManagerInfo user = managerInfoService.findByUsername(username);
        //随机数盐
        String salt = user.getSalt();
        //原密码加密（通过username + salt作为盐）
        String encodedPassword = ShiroKit.md5(password, username + salt);
        if (user.getPassword().equals(encodedPassword)) {
            return new BaseResponse<>(true, "Login success", JWTUtil.sign(username, encodedPassword));
        } else {
            throw new UnauthorizedException();
        }
    }
}
