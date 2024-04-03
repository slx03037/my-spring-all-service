package netty.xml.demo02.resp;

import io.netty.handler.codec.http.FullHttpResponse;
import lombok.Data;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:46
 */
@Data
public class HttpXmlResponse {
    private FullHttpResponse httpResponse;
    private Object result;

    public HttpXmlResponse(FullHttpResponse fullHttpResponse, Object result){
        this.httpResponse = fullHttpResponse;
        this.result = result;
    }
}
