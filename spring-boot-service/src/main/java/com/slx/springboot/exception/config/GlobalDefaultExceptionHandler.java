package com.slx.springboot.exception.config;

import cn.hutool.json.JSONObject;
import com.shenlx.xinwen.common.base.CommonResult;
import com.shenlx.xinwen.common.base.ResultCode;
import com.slx.springboot.exception.exception.UserNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-10 22:35
 **/
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public CommonResult defaultErrorHandler(HttpServletRequest req, Exception e) {
        log.info("http的请求参数：{},异常原因，{}", new JSONObject(req).toString(),e.getMessage());
        return new CommonResult(ResultCode.FAILED.getCode(),ResultCode.FAILED.getMessage(),null);
    }

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUserNotExistsException(UserNotExistException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", e.getId());
        map.put("message", e.getMessage());
        return map;
    }
}
