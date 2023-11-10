package com.shenlx.xinwen.springbootelasticsearchrestclient.controller;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-08 17:31
 */
@RestController
@Slf4j
public class DeleteController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private ElasticsearchAsyncClient elasticsearchAsyncClient;
    /**
     * 根据id删除索引products下的文档doc
     */
    @RequestMapping("delete")
    public String delete() throws IOException {
        // 创建删除请求对象
        DeleteRequest deleteRequest = new DeleteRequest.Builder()
                .index("products")
                .id("bk-5")
                .build();
        elasticsearchClient.delete(deleteRequest);
        elasticsearchClient.indices().delete(s -> s.index("goods"));
        return "success";
    }

    /**
     * 删除文档
     * @return
     * @throws IOException
     */
    @RequestMapping("deleteDoc")
    public String deleteDoc() throws IOException {
        // 创建删除请求对象
        DeleteResponse deleteResponse= elasticsearchClient.delete(s -> s.index("products").id("bk-4"));
        return "success";
    }

    /**
     * 删除索引
     * @return
     * @throws IOException
     */
    @RequestMapping("deleteIndex")
    public String deleteIndex() throws IOException {
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices().delete(s -> s.index("goods"));
        log.info("删除索引操作结果：{}",deleteIndexResponse.acknowledged());
        return "success";
    }

    /**
     *  批量删除文档 方式一
     * @return
     */
    @RequestMapping("deleteStream")
    public String deleteStream() throws IOException {
        // 方法1、use BulkOperation
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        List<BulkOperation> bulkOperations = new ArrayList<>();
        list.forEach(x->
                bulkOperations.add(
                        BulkOperation.of(
                                b->b.delete(c->c.id(x)))
                )
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(a -> a.index("users").operations(bulkOperations));
        bulkResponse.items().forEach(a ->
                log.info("result = {}" , a.result()));
        log.error("bulkResponse.errors() = {}" , bulkResponse.errors());
        return "success";
    }
    @RequestMapping("deleteBatch")
    public String deleteBatch() throws IOException{
        BulkRequest.Builder br = new BulkRequest.Builder();
        List<String> list = new ArrayList<>();
        list.add("3");
        list.add("4");
        list.forEach(
                x->
                        br.operations(op->
                                op.delete(c->c.index("users").id(x)
                                )
                        )
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(br.build());
        bulkResponse.items().forEach(
                a->
                        log.info("result = {}" , a.result())
        );
        log.error("bulkResponse.errors() = {}" , bulkResponse.errors());
        return "success";
    }

}
