package com.slx.elasticsearch.config;

import com.shenlx.xinwen.common.base.CommonResult;
import com.slx.elasticsearch.until.ESClientUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 12:12
 **/
public class ConnectionConfig {
    public static final String hostname="192.168.3.39";
    public static final Integer port=9200;
    public void connect() throws IOException {
        // 3、创建HttpHost
        HttpHost host = new HttpHost(hostname, port);// 发现需要：String hostname, int port  这就很简单了涩
        // 当然：这个方法重载中有一个参数scheme  这个是：访问方式 根据需求用http / https都可以  这里想传的话用：http就可以了
        // 2、创建RestClientBuilder 但是：点击源码发现 - 没有构造方法
        // 既然没有，那肯定提供得有和xml版的mybatis加载完xml文件之后的builder之类的，找一下
        RestClientBuilder clientBuilder = RestClient.builder(host);
        // 发现1、有重载；2、重载之中有几个参数，而HttpHost... hosts 这个参数貌似贴近我们想要的东西了，所以建一个HttpHost
        // 1、要链接client，那肯定需要一个client咯，正好：导入得有high-level-client
        RestHighLevelClient esClient = new RestHighLevelClient(clientBuilder);   // 发现需要RestClientBuilder，那就建
        // 4、测试：只要 esClient. 就可以看到一些很熟悉的方法，可以在这里测试调一下哪些方法，然后去postman中获取数据看一下对不对
        // 这里不多做说明：java链接ES客户端的流程就是上面这样的，不过：这和MySQL数据库链接一样，记得不用了就关闭
        esClient.close();       // 当然：封装之后，这个关闭操作应该放出来，然后在封装的工具中只需要返回这个链接对象即可
    }

    //创建索引
    public static void main(String[]args) throws IOException {
        new ConnectionConfig().connect();

    }
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
