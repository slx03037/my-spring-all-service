package com.shenlx.xinwen.springbootresttemplatepush.service;

import com.shenlx.xinwen.common.base.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-10 12:06
 **/
@Service
public class MessageGetServiceImpl implements MessageGetService{
    private static Logger log= LoggerFactory.getLogger(MessageGetServiceImpl.class);

    private  final String pathUrl="http://127.0.0.1:8082";

    @Override
    public String getString(){
        ResponseEntity<CommonResult> responseEntity=new RestTemplate().getForEntity(pathUrl+"/get", CommonResult.class);
        log.info("展示结果为：{}",responseEntity);
        CommonResult body = responseEntity.getBody();
        return body.getMessage();

    }
}
