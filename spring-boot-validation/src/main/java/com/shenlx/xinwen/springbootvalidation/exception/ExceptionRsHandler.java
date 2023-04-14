package com.shenlx.xinwen.springbootvalidation.exception;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-04-14 16:25
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数在校验失败的时候会抛出的 MethodArgumentNotValidException 或者 BindException 两种异常，可以在
 * 全局的异常处理器中捕捉到这两种异常，将提示信息或者自定义信息返回给客户端
 */
@RestControllerAdvice
public class ExceptionRsHandler {
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 参数校验异常步骤
     */
    @ExceptionHandler(value= {MethodArgumentNotValidException.class , BindException.class})
    public String onException(Exception e) throws JsonProcessingException {
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException)e).getBindingResult();
        }
        Map<String,String> errorMap = new HashMap<>(16);
        bindingResult.getFieldErrors().forEach((fieldError)->
                errorMap.put(fieldError.getField(),fieldError.getDefaultMessage())
        );
        return objectMapper.writeValueAsString(errorMap);
    }

}
