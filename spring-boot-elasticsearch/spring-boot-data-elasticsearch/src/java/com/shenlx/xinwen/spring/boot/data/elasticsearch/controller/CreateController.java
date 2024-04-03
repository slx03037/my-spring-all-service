package com.shenlx.xinwen.spring.boot.data.elasticsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shenlx.xinwen.spring.boot.data.elasticsearch.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-10 16:40
 */
@RestController
@Slf4j
public class CreateController {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @return
     * @throws IOException
     */
    @RequestMapping("/createIndex")
    public String createIndex() throws IOException {
        CreateIndexRequest  createRequest=new CreateIndexRequest("goods");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createRequest, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        return "success";
    }

    /**
     * 新增文档
     * @return
     */
    @RequestMapping("addDoc")
    public String addDoc() throws JsonProcessingException,IOException {
        Goods goods=new Goods(1000L,"MAC",new BigDecimal(20000.00),20,10,"电脑","苹果",1,new Date());
        IndexRequest request = new IndexRequest();
        request.index("goods").id(goods.getSku().toString());
        //向ES插入数据，必须将数据转换为JSON格式
        ObjectMapper mapper= new ObjectMapper();
        String json = mapper.writeValueAsString(goods);
        request.source(json, XContentType.JSON);
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info("输出结果：{}",index.getResult());
        return "success";
    }

}
