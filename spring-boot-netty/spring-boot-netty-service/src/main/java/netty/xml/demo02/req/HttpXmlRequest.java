package netty.xml.demo02.req;

import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:45
 */

/**
 * 它包含两个成员变量 FullHttpRequest和编码对象Object ，用于实现和协议栈之间的解耦
 */
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpXmlRequest{" +
                "request=" + request +
                ", body=" + body +
                '}';
    }
}
