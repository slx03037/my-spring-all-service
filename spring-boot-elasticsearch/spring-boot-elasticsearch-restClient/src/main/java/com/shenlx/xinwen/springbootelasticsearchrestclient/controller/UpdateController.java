package com.shenlx.xinwen.springbootelasticsearchrestclient.controller;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.shenlx.xinwen.springbootelasticsearchrestclient.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-08 17:29
 */
@RestController
@Slf4j
public class UpdateController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private ElasticsearchAsyncClient elasticsearchAsyncClient;
    /**
     * 修改products索引下的id为bk-1
     */
    @RequestMapping("update")
    public String update() throws IOException {
        Product product = new Product("bk-01", "update", 188.01);
        // 创建修改对象
        UpdateRequest updateRequest = new UpdateRequest.Builder().index("products")
                .id("bk-1")
                .doc(product)
                .build();
        elasticsearchClient.update(updateRequest, Product.class);
        return "success";
    }

    @RequestMapping("updateDoc")
    public String updateDoc() throws IOException {
        Product product = new Product("bk-01", "update", 188.01);
        // 创建修改对象
        // 构建修改文档的请求
        UpdateResponse<Product> response = elasticsearchClient.update(e -> e
                .index("products")
                .id("bk-3")
                .doc(product),Product.class);
        log.info("update result:{}",response.result());
        return "success";
    }
}
