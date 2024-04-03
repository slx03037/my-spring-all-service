package netty.xml.demo01.utils.request;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 14:50
 */
public class HttpXmlRequest {
    /**
     * FullHttpRequest和Object，用于实现和协议栈之间得解耦
     */
    private FullHttpRequest request;

    private Object body;

    public HttpXmlRequest(FullHttpRequest request,Object body){
        this.request=request;
        this.body=body;
    }

    public final FullHttpRequest getRequest() {
        return request;
    }

    public final void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }
}
