package com.shenlx.xinwen.jwt.cache.api;

import com.shenlx.xinwen.jwt.cache.model.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-04 15:13
 **/
@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class PublicController {
    /**
     * 入网绑定查询接口
     *
     * @return 是否入网
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    @RequiresAuthentication
    public BaseResponse join(@RequestParam("imei") String imei) {
        log.info("入网查询接口 start... imei=" + imei);
        BaseResponse result = new BaseResponse();
        result.setSuccess(true);
        result.setMsg("已入网并绑定了网点");
        return result;
    }
}
