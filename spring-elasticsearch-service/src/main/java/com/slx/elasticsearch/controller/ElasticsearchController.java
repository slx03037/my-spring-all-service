package com.slx.elasticsearch.controller;

import com.shenlx.xinwen.common.base.CommonResult;
import com.slx.elasticsearch.until.ESClientUtil;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 13:43
 **/
@Controller
@RequestMapping("es")
public class ElasticsearchController {


    @PostMapping("/createIndex")
    public CommonResult createIndex() throws IOException {
        RestHighLevelClient esClient = ESClientUtil.getESClient();
        try {
            // 创建索引
            // CreateIndexRequest()  第一个参数：要创建的索引名    第二个参数：请求选项  默认即可
            CreateIndexResponse response = esClient.indices().create(
                    new CreateIndexRequest("person"), RequestOptions.DEFAULT);
            // 查看是否添加成功  核心方法：isAcknowledged()
            System.out.println(response.isAcknowledged());

        }finally {
            esClient.close();
        }
        return  CommonResult.success();
    }
}
