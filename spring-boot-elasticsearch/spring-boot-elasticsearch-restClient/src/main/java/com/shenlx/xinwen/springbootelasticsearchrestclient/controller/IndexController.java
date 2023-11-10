package com.shenlx.xinwen.springbootelasticsearchrestclient.controller;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shenlx.xinwen.springbootelasticsearchrestclient.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-08 17:27
 */
@RestController
@Slf4j
public class IndexController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private ElasticsearchAsyncClient elasticsearchAsyncClient;

    //获取索引
    @RequestMapping("getIndex")
    public String getIndex() {
        try {
            // 查看指定索引
            GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(s -> s.index("products"));
            Map<String, IndexState> result = getIndexResponse.result();
            result.forEach((k, v) -> log.info("key = {},value = {}", k, v));

            // 查看全部索引
            IndicesResponse indicesResponse = elasticsearchClient.cat().indices();
            // 返回对象具体查看 co.elastic.clients.elasticsearch.cat.indices.IndicesRecord
            indicesResponse.valueBody().forEach(
                    info -> log.info("health:{}\n status:{} \n uuid:{} \n ", info.health(), info.status(), info.uuid())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }
}
