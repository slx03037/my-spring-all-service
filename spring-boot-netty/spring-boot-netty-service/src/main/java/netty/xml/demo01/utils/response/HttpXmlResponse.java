package netty.xml.demo01.utils.response;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 15:30
 */
public class HttpXmlResponse {
    private FullHttpResponse httpResponse;

    private Object result;

    public  HttpXmlResponse(FullHttpResponse httpResponse,Object result){
        this.httpResponse=httpResponse;
        this.result=result;
    }

    public final FullHttpResponse getHttpResponse() {
        return httpResponse;
    }

    public final void setHttpResponse(FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public final Object getResult() {
        return result;
    }

    public final void setResult(Object result) {
        this.result = result;
    }
}
