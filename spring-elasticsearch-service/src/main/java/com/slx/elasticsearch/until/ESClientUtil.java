package com.slx.elasticsearch.until;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-06 13:31
 **/

public class ESClientUtil {
    private static final String HOST = "192.168.3.39";     // 用localhost也行，不过后面用linux就要ip，所以：算在这里养成习惯吧
    private static final Integer PORT = 9200;
    public static RestHighLevelClient getESClient() {
        return new RestHighLevelClient( RestClient.builder( new HttpHost( HOST, PORT ) ) );
    }
}
