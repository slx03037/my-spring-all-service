package netty.xml.demo01.utils.response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import netty.xml.demo01.utils.code.AbstractHttpXmlDecoder;

import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 15:41
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {

    public HttpXmlResponseDecoder(Class<?> clazz){
        this(clazz,false);
    }

    public HttpXmlResponseDecoder(Class<?>clazz,boolean isPrintLog){
        super(clazz,isPrintLog);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
        HttpXmlResponse response = new HttpXmlResponse(msg, decode0(ctx, msg.content()));
        out.add(response);
    }
}
