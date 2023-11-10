package com.shenlx.xinwen.springbootelasticsearchrestclient.controller;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.SearchTemplateResponse;
import co.elastic.clients.elasticsearch.core.search.*;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.endpoints.BinaryResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shenlx.xinwen.springbootelasticsearchrestclient.entity.Product;
import com.shenlx.xinwen.springbootelasticsearchrestclient.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author shenlx
 * @version 1.0.0
 * @date 2023-11-08 17:28
 */
@RestController
@Slf4j
public class QueryController {
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    private LongTermsAggregate longTermsAggregate;

    /**
     * 创建索引为goods2
     */
    @RequestMapping("query")
    public String queryIndex() {
        //使用 * 或者 _all都可以
        GetIndexResponse response = null;
        try {
            response = elasticsearchClient.indices().get(builder -> builder.index("_all"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        log.info("getIndexList方法，response.result()={}", response.result().toString());
        return response.result().toString();
    }

    /**
     * 获取索引product的文档doc内容  一行数据
     * 读取域对象
     */
    @RequestMapping("getDoc")
    public Product getDoc() {
        Product product = null;
        try {
            /**
             * 下面的示例从索引中读取带有标识符的文档。bk-1products
             *
             * 该请求有两个参数：get
             *
             * 第一个参数是实际请求，下面使用 Fluent DSL 构建
             * 第二个参数是我们希望文档的 JSON 映射到的类
             */
            GetResponse<Product> response = elasticsearchClient.get(g -> g
                            .index("products")
                            .id("bk-1"),
                    Product.class
            );
            if (response.found()) {
                product = response.source();
                log.info("Product name " + product.getSku());
            } else {
                log.info("Product not found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * 读取原始 JSON
     */
    @RequestMapping("getJson")
    public ObjectNode getJson() throws IOException {
        ObjectNode json = null;
        GetResponse<ObjectNode> response = elasticsearchClient.get(g -> g
                        .index("products")
                        .id("bk-2"),
                ObjectNode.class
        );

        if (response.found()) {
            json = response.source();
            String name = json.get("sku").asText();
            log.info("Product name " + name);
        } else {
            log.info("Product not found");
        }
        return json;
    }

    /**
     * 判断文档是否存在
     */
    @RequestMapping("/booleanByIndexToId")
    public Boolean booleanResponse() throws IOException {
        BooleanResponse booleanResponse = elasticsearchClient.exists(s -> s.index("users").id("bk-1"));
        log.info("判断Document是否存在:{}", booleanResponse.value());
        return booleanResponse.value();
    }

    //-------------------------------------------------高级查询---------------------------

    /**
     * 单调查询
     */
    @RequestMapping("queryByField")
    public User query() throws IOException {
        String searchText = "赵高";
        SearchResponse<User> response = elasticsearchClient.search(s -> s
                        // 我们要搜索的索引的名称
                        .index("users")
                        // 搜索请求的查询部分（搜索请求也可以有其他组件，如聚合）
                        .query(q -> q
                                // 在众多可用的查询变体中选择一个。我们在这里选择匹配查询（全文搜索）
                                .match(t -> t
                                        // name配置匹配查询：我们在字段中搜索一个词
                                        .field("name")
                                        .query(searchText)
                                )
                        ),
                // 匹配文档的目标类
                User.class
        );
        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;
        if (isExactResult) {
            log.info("There are " + total.value() + " results");
        } else {
            log.info("There are more than " + total.value() + " results");
        }
        List<Hit<User>> hits = response.hits().hits();
        User user = null;
        for (Hit<User> hit : hits) {
            user = hit.source();
            log.info("Found product " + user.getName() + ", score " + hit.score());
        }
        return user;
    }

    /**
     * 嵌套搜索查询
     */
    @RequestMapping("nestQuery")
    public User nestQuery() throws IOException {
        User user = null;
        String searchText = "赵高";
        int age = 20;
        // name、age1：分别为各个条件创建查询
        Query name = MatchQuery.of(m -> m
                .field("name")
                .query(searchText)
        )._toQuery();// //MatchQuery是一个查询变体，我们必须将其转换为 Query 联合类型
        Query age1 = RangeQuery.of(m -> m
                .field("age")
                .gte(JsonData.of(age))// Elasticsearch 范围查询接受大范围的值类型。我们在这里创建最高价格的 JSON 表示。
        )._toQuery();
        SearchResponse<User> users = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .bool(b -> b
                                        .must(name)// // 搜索查询是结合了文本搜索和最高价格查询的布尔查询
                                        .must(age1)
                                )
                        )
                , User.class
        );

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("users")
                .query(q -> q
                        .bool(b -> b
                                .must(name)
                                .must(age1)
                        )
                )
        );

        SearchResponse<User> search = elasticsearchClient.search(searchRequest, User.class);
        List<Hit<User>> hits = search.hits().hits();
        for (Hit<User> hit : hits) {
            user = hit.source();
            assert user != null;
            log.info("foud userId:{},name:{}", user.getId(), user.getName());
        }
        return user;
    }

    /**
     * 模板化搜索
     * 模板化搜索是存储的搜索，可以使用不同的变量运行它。搜索模板让您无需修改应用程序代码即可更改搜索。
     * 在运行模板搜索之前，首先必须创建模板。这是一个返回搜索请求正文的存储脚本，通常定义为 Mustache 模板
     */
    @RequestMapping("templateQuery")
    public User templateQuery() throws IOException {
        User user = null;
        //事先创建搜索模板
        elasticsearchClient.putScript(r -> r
                .id("query-script")
                .script(s -> s
                        .lang("Mustache")
                        .source("{\"query\":{\"match\":{\"{{filed}}\":\"{{value}}\"}}}")
                ));
        //使用模板
        String filed = "name";
        String value = "赵高";

        SearchTemplateResponse<User> userSearchTemplateResponse = elasticsearchClient.searchTemplate(r -> r
                        .index("users")
                        .id("query-script") //使用模板得标识
                        .params("filed", JsonData.of(filed))
                        .params("value", JsonData.of(value)),
                User.class
        );

        List<Hit<User>> hits = userSearchTemplateResponse.hits().hits();
        for (Hit<User> hit : hits) {
            user = hit.source();
            assert user != null;
            log.info("foud userId:{},name:{}", user.getId(), user.getName());
        }

        return user;
    }

    /**
     * 分页&排序查询
     */
    @RequestMapping("pageQuery")
    public List<User> pageQuery() throws IOException {
        List<User> users = new ArrayList<>();
        Query age = RangeQuery.of(r -> r
                .field("age")
                .gte(JsonData.of(20))
        )._toQuery();
        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index("users")
                .query(q -> q
                        .bool(b -> b.
                                must(age)
                        )
                )
                //分页查询，从第0页开始查询4个document
                .from(0)
                .size(4)
                //按age降序排序
                .sort(f -> f.field(o -> o.field("age")
                        .order(SortOrder.Desc)
                )));
        SearchResponse<User> search = elasticsearchClient.search(searchRequest, User.class);

//        SearchResponse<User> searchResponse=elasticsearchClient.search(s->s
//                .index("users")
//                .query(q->q
//                        .bool(b->b.
//                                must(age)
//                        )
//                )
//                //分页查询，从第0页开始查询4个document
//                .from(0)
//                .size(4)
//                //按age降序排序
//                .sort(f->f.field(o->o.field("age")
//                        .order(SortOrder.Desc)
//                )))
//                ,User.class
//        );

        List<Hit<User>> hits = search.hits().hits();
        for (Hit<User> hit : hits) {
            User user = hit.source();
            assert user != null;
            users.add(user);
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
        return users;
    }

    /**
     * 查询所有并进行分页&排序
     */
    @RequestMapping("queryAllPageOrder")
    public List<User> queryAllPageOrder() throws IOException {
        List<User> users = new ArrayList<>();
        Query age = RangeQuery.of(r -> r
                .field("age")
                .gte(JsonData.of(19))
        )._toQuery();
        SearchResponse<User> search = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .matchAll(m -> m)
                        )
                        .from(0)
                        .size(10)
                        .sort(f -> f
                                .field(o -> o
                                        .field("age")
                                        .order(SortOrder.Desc)
                                )
                        )
                , User.class
        );
        HitsMetadata<User> hits1 = search.hits();
        log.info("search.hits:{}", hits1);
        List<Hit<User>> hits = search.hits().hits();
        for (Hit<User> hit : hits) {
            User user = hit.source();
            assert user != null;
            users.add(user);
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
        return users;
    }

    /**
     * 过滤字段
     */
    @RequestMapping("queryFilterField")
    public User queryFilterField() throws IOException {
        User user = null;
        Query query = MatchQuery.of(r -> r
                .field("name")
                .query("赵高")
        )._toQuery();
        SearchResponse<User> searchResponse = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .bool(b -> b
                                        .must(query)
                                )
                        )
                        .source(so -> so
                                .filter(f -> f
                                        .includes("age", "id")
                                        .excludes("")
                                )
                        )
                , User.class
        );
        List<Hit<User>> hits = searchResponse.hits().hits();
        for (Hit<User> hit : hits) {
            user = hit.source();
            assert user != null;
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
        return user;
    }

    /**
     * 模糊查询
     */
    @RequestMapping("fuzzyQuery")
    public List<User> fuzzyQuery() throws IOException {
        List<User> users = new ArrayList<>();
        SearchResponse<User> search = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                // 模糊查询
                                .fuzzy(f -> f
                                        // 需要判断的字段名称
                                        .field("name")
                                        // 需要模糊查询的关键词
                                        // 目前文档中没有liuyi这个用户名
                                        .value("祖")
                                        // fuzziness代表可以与关键词有误差的字数，可选值为0、1、2这三项
                                        .fuzziness("2")
                                )
                        )
                , User.class
        );
        List<Hit<User>> hits = search.hits().hits();
        for (Hit<User> hit : hits) {
            User user = hit.source();
            assert user != null;
            users.add(user);
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
        return users;
    }

    /**
     * 高亮查询
     */
    @RequestMapping("highlightQuery")
    public User highlightQuery() throws IOException {
        User user = null;
        SearchResponse<User> search = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value("赵高")
                                )
                        )
                        .highlight(h -> h
                                .fields("name", f -> f
                                        .preTags("<font color='red'>")
                                        .postTags("</font>")
                                )
                        )
                , User.class
        );
        List<Hit<User>> hits = search.hits().hits();
        for (Hit<User> hit : hits) {
            user = hit.source();
        }
        log.info("模糊查询结果：{}", (user));
        return user;
    }
    //---------------聚合查询-------------------

    /**
     * 最大值
     */
    @RequestMapping("getMaxAgeUser")
    public User getMaxAgeUser() throws IOException {
        User user = null;
        SearchResponse<User> search = elasticsearchClient.search(s -> s
                        .index("users")
                        // .size(10)
                        .aggregations("maxAge", a -> a
                                .max(MaxAggregation.of(m -> m.field("age")
                                        )
                                )
                        )
                , User.class
        );
        MaxAggregate maxAge = search.aggregations()
                .get("maxAge")
                .max();
        log.info("最大值：{}", maxAge.value());
        List<Hit<User>> hits = search.hits().hits();
        for (Hit<User> hit : hits) {
            user = hit.source();
        }
        return user;
    }

    /**
     * 分组统计
     */
    @RequestMapping("groupingQuery")
    public Buckets<LongTermsBucket> groupingQuery() throws IOException {
        SearchResponse<User> search = elasticsearchClient.search(s -> s
                        .index("users")
                        .aggregations("groupName", a -> a
                                .terms(TermsAggregation.of(t ->
                                                t.field("age")
                                        )
                                )
                        )
                , User.class
        );
        LongTermsAggregate longTermsAggregate = search.aggregations().get("groupName").lterms();
        Buckets<LongTermsBucket> buckets = longTermsAggregate.buckets();
        log.info("multiTermsAggregate:{}", buckets);
        return buckets;
    }

    /**
     * 分组统计
     * @throws IOException
     */
    @RequestMapping("/groupBySex")
    public void groupBySex() throws IOException {
        SearchResponse<User> search = elasticsearchClient.search(s -> s
                        .index("users")
                        .aggregations("groupSex", a -> a
                                .terms(TermsAggregation.of(t -> t
                                                .field("sex.keyword")
                                        )
                                )
                        )
                , User.class
        );
        StringTermsAggregate groupSex = search.aggregations()
                .get("groupSex")
                .sterms();
        log.info("LongTermsAggregate:{}",groupSex.buckets());
    }
}
