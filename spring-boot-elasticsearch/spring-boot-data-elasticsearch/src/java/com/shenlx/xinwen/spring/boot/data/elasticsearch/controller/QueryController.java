package com.shenlx.xinwen.spring.boot.data.elasticsearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-10 23:49
 */
@RestController
@Slf4j
public class QueryController {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 查询索引
     * @return
     */
    @RequestMapping("/getIndex")
    public String getIndex() throws IOException {
        GetIndexRequest request=new GetIndexRequest("goods");
        GetIndexResponse respone = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        log.info("respone:{}",respone.getMappings());
        log.info("respone:{}",respone.getIndices());
        log.info("respone:{}",respone.getSettings());
        log.info("respone:{}",respone.getAliases());
        return "success";
    }
}
