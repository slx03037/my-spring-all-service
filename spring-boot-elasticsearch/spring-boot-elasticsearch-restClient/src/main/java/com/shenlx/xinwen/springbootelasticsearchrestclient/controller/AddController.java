package com.shenlx.xinwen.springbootelasticsearchrestclient.controller;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._helpers.bulk.BulkIngester;
import co.elastic.clients.elasticsearch._helpers.bulk.BulkListener;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;
import com.shenlx.xinwen.springbootelasticsearchrestclient.entity.Product;
import com.shenlx.xinwen.springbootelasticsearchrestclient.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-07 16:38
 */
@RestController
@Slf4j
public class AddController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private ElasticsearchAsyncClient elasticsearchAsyncClient;

    /**
     * 创建索引为goods2
     */
    @RequestMapping("create")
    public String createIndex() {
        // 执行增加文档
        try {
            CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(c -> c.index("goods2"));
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    @RequestMapping("create1")
    public String createIndex1() {
        // 执行增加文档
        try {
            Product product = new Product("bk-1", "City bike", 123.0);
            //方式一使用 DSL 创建的对象分配给变量
            IndexRequest<Product> request = IndexRequest.of(i -> i
                    .index("products")
                    .id(product.getSku())
                    .document(product)
            );
            IndexResponse response1 = elasticsearchClient.index(request);
            log.info("Indexed with version :{}", response1.version());
            IndexResponse response = elasticsearchClient.index(i -> i
                    .index(Product.class.getSimpleName().toLowerCase(Locale.ROOT))
                    .id(product.getSku())
                    .document(product)
            );
            log.info("Indexed with version :{}", response.version());
            //方式仨 使用经典构建器
            IndexRequest.Builder<Product> indexReqBuilder = new IndexRequest.Builder<>();
            indexReqBuilder.index("productss");
            indexReqBuilder.id(product.getSku());
            indexReqBuilder.document(product);

            IndexResponse response2 = elasticsearchClient.index(indexReqBuilder.build());
            log.info("Indexed with version :{}", response2.version());

            //方式4 使用异步客户端
            elasticsearchAsyncClient.index(s -> s
                    .index("product4")
                    .id(product.getSku())
                    .document(product)
            ).whenComplete((response4, exception) -> {
                if (exception != null) {
                    log.error("Failed to index:{}", exception);
                } else {
                    log.info("Indexed with version " + response4.version());
                }
            });

            //方式5 使用原始 JSON 数据
            Reader input = new StringReader(
                    "{'@timestamp': '2022-04-08T13:55:32Z', 'level': 'warn', 'message': 'Some log message'}"
                            .replace('\'', '"'));

            IndexRequest<JsonData> request1 = IndexRequest.of(s -> s
                    .index("logs")
                    .withJson(input)
            );

            IndexResponse response5 = elasticsearchClient.index(request);

            log.info("Indexed with version:{} ", response5.version());
        } catch (IOException e) {
            e.printStackTrace();
            return "failure";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }



    /**
     * 添加文档
     *
     * @return
     */
    @RequestMapping("add")
    public String addDoc() {
        try {
            Product product = new Product("bk-2", "save doc", 121.0);
            CreateRequest<Product> request = CreateRequest.of(i -> i
                    .index("products")
                    .id(product.getSku())
                    .document(product)
            );
            elasticsearchClient.create(request);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    /**
     * 批量流式新增
     * @return
     * @throws IOException
     */
    //方式一
    @RequestMapping("addStream")
    public String addBatch() throws IOException {

        List<User> users = new ArrayList<>();
//        users.add(new User("1","赵四",20,"男"));
//        users.add(new User("2","阿旺",25,"男"));
//        users.add(new User("3","刘菲",22,"女"));
//        users.add(new User("4","冬梅",20,"女"));

        users.add(new User("11","zhaosi",20,"男"));
        users.add(new User("22","awang",25,"男"));
        users.add(new User("33","liuyifei",22,"女"));
        users.add(new User("44","dongmei",20,"女"));
        users.add(new User("55","zhangya",30,"女"));
        users.add(new User("66","liuyihu",32,"男"));
        //方式一
        List<BulkOperation> bulkOperations=new ArrayList<>();
        users.forEach(u ->
                bulkOperations.add(BulkOperation.of(b->
                        b.index(
                                c->
                                        c.id(u.getId())
                                                .document(u)
                        )))
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(s -> s.index("users").operations(bulkOperations));
        bulkResponse.items().forEach(i ->
                log.info("i = {}" , i.result()));
        log.error("bulkResponse.errors() = {}" , bulkResponse.errors());
        return "success";
    }

    //方式二
    @RequestMapping("/addBatch")
    public String addBatchByIndexDoc() {
        List<Product> products = fetchProducts();

        BulkRequest.Builder br = new BulkRequest.Builder();

        /**
         * 添加操作（请记住，列表属性是累加的）。 is 是一个生成器，其是变体类型。此类型具有 、 和 变体。op Bulk Operation index create update delete
         * 选择操作变型，是 的构建器。index idx IndexOperation
         * 设置索引操作的属性，类似于单个文档索引：索引名称、标识符和文档。
         */
        for (Product product : products) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index("products")
                            .id(product.getSku())
                            .document(product)
                    )
            );
        }

        BulkResponse result = null;
        try {
            result = elasticsearchClient.bulk(br.build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
        return "success";
    }

    public List<Product> fetchProducts() {
        List<Product> list = new ArrayList<>();
        Product product1 = new Product("bk-3", "save doc111", 131.0);
        Product product2 = new Product("bk-4", "save doc112", 132.0);
        Product product3 = new Product("bk-5", "save doc113", 133.0);
        Product product4 = new Product("bk-6", "save doc114", 134.0);
        Product product5 = new Product("bk-7", "save doc115", 135.0);
        list.add(product1);
        list.add(product2);
        list.add(product3);
        list.add(product4);
        list.add(product5);
        return list;
    }

    /**
     * 为原始 JSON 数据编制索引
     */
    public void addJosnData() throws IOException {
        /**
         * 批量索引请求的属性可以是可以使用 Elasticsearch 客户端的 JSON 映射器序列化为 JSON 的任何对象。
         * 但是，批量引入的数据通常以 JSON 文本形式提供（例如磁盘上的文件），解析此 JSON 只是为了重新序列化它以发送批量请求将浪费资源。
         * 因此，批量操作中的文档也可以是逐字发送（不解析）到 Elasticsearch 服务器的类型。documentBinaryData
         */
        // List json log files in the log directory
        //获取 /data/log 下的目录
        File logDir = new File("/data/log");
        //遍历 /data/log  目录下的文件前缀为log，后缀为.json的文件
        File[] logFiles = logDir.listFiles(
                file -> file.getName().matches("log-.*\\.json")
        );

        BulkRequest.Builder br = new BulkRequest.Builder();

        for (File file : logFiles) {
            FileInputStream input = new FileInputStream(file);
            //将文件流转为byte数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, temp);
            }
            byte[] finalBytes = outputStream.toByteArray();
            BinaryData data = BinaryData.of(finalBytes, ContentType.APPLICATION_JSON);

            br.operations(op -> op
                    .index(idx -> idx
                            .index("logs")
                            .document(data)
                    )
            );
        }
    }

    /**
     * 使用 Bulk Ingester 进行流式摄取
     */
    public String bulkIngester() throws IOException {
        /**
         *  它通过提供一个实用程序类来简化批量 API 的使用，该实用程序类允许在批量请求中透明地对索引/更新/删除操作进行分组。
         *您只需要对 ingester 进行批量操作，并且 它将负责根据其配置对它们进行分组并批量发送。BulkIngesteradd()
         *
         *  当满足以下条件之一时，ingester 将发送批量请求：
         *
         *  操作数超过最大值（默认为 1000）
         *  批量请求大小（以字节为单位）超过最大值（默认为 5 MiB）
         *  自上次请求过期以来的延迟（定期刷新，无默认值）
         *  此外，您还可以定义等待 Elasticsearch 执行的最大并发请求数（默认为 1）。当达到该最大值并收集了最大操作数时，
         *向索引器添加新操作将受阻。这样可以避免通过对客户端应用程序施加背压来使 Elasticsearch 服务器过载。
         */

        /**
         * 1.设置用于发送批量请求的 Elasticsearch 客户端。
         * 2.设置在发送批量请求之前要收集的最大操作数
         * 3.设置刷新间隔。
         */
        BulkIngester<Void> ingester = BulkIngester.of(b -> b
                .client(elasticsearchClient)
                .maxOperations(100)
                .flushInterval(1, TimeUnit.SECONDS)
        );
        File logDir = new File("/data/log");
        File[] logFiles = logDir.listFiles(
                file -> file.getName().matches("log-.*\\.json")
        );
        for (File file : logFiles) {
            FileInputStream input = new FileInputStream(file);
            //将文件流转为byte数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, temp);
                //count += n;
            }
            byte[] finalBytes = outputStream.toByteArray();
            BinaryData data = BinaryData.of(finalBytes, ContentType.APPLICATION_JSON);
            //4.向 ingester 添加批量操作。
            ingester.add(op -> op
                    .index(idx -> idx
                            .index("logs")
                            .document(data)
                    )
            );
        }
        //5.关闭引入器以刷新挂起的操作并释放资源。
        ingester.close();
        return "sucess";
    }

    public String listenerBulkIngester() throws IOException {
        //创建一个侦听器，其中上下文值是引入的文件名的字符串
        BulkListener<String> listener = new BulkListener<String>() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request, List<String> contexts) {
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, List<String> contexts, BulkResponse response) {
                // The request was accepted, but may contain failed items.
                // The "context" list gives the file name for each bulk item.
                log.debug("Bulk request " + executionId + " completed");
                for (int i = 0; i < contexts.size(); i++) {
                    BulkResponseItem item = response.items().get(i);
                    if (item.error() != null) {
                        // Inspect the failure cause
                        log.error("Failed to index file " + contexts.get(i) + " - " + item.error().reason());
                    }
                }
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, List<String> contexts, Throwable failure) {
                // The request could not be sent
                log.debug("Bulk request " + executionId + " failed", failure);
            }
        };
        BulkIngester<String> ingester = BulkIngester.of(b -> b
                .client(elasticsearchClient)
                .maxOperations(100)
                .flushInterval(1, TimeUnit.SECONDS)
                .listener(listener) //在批量摄取器上注册侦听器。
        );

        //获取 /data/log 下的目录
        File logDir = new File("/data/log");
        //遍历 /data/log  目录下的文件前缀为log，后缀为.json的文件
        File[] logFiles = logDir.listFiles(
                file -> file.getName().matches("log-.*\\.json")
        );
        for (File file : logFiles) {
            FileInputStream input = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int temp;
            while ((temp = input.read(buffer)) != -1) {
                outputStream.write(buffer, 0, temp);
                //count += n;
            }
            byte[] finalBytes = outputStream.toByteArray();
            BinaryData data = BinaryData.of(finalBytes, ContentType.APPLICATION_JSON);

            ingester.add(op -> op
                            .index(idx -> idx
                                    .index("logs")
                                    .document(data)
                            ),
                    file.getName() //将文件名设置为批量操作的上下文值。
            );
        }

        ingester.close();
        /**
         * 批量引入还会公开统计信息，这些信息允许监视引入过程并调整其配置：
         *
         * 添加的操作数，
         * 由于达到最大并发请求数（争用），add()
         * 发送的批量请求数，
         * 由于达到最大并发请求数而被阻止的批量请求数
         */
        return "success";
    }
}
