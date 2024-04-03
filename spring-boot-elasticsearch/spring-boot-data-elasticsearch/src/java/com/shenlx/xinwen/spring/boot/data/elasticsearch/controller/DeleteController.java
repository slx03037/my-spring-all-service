package com.shenlx.xinwen.spring.boot.data.elasticsearch.controller;

import lombok.Data;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-10 16:42
 */
@RestController
public class DeleteController {
    @Resource
    private  RestHighLevelClient restHighLevelClient;
    /**
     * 删除索引
     */
    @RequestMapping("deleteIndex")
    public void deleteIndex() throws IOException {
        String[] args={"productss","product4","goodss","product","goods2"};
        List<String> list= Arrays.asList(args);

        for(String index:list){
            DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest();
            deleteIndexRequest.indices(index);
            restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        }

    }
}
