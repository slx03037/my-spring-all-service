package netty.xml.demo01.utils.response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import netty.xml.demo01.utils.code.AbstractHTTPXmlEncoder;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 15:43
 */
public class HttpXmlResponseEncoder extends AbstractHTTPXmlEncoder<HttpXmlResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg.getResult());
        FullHttpResponse response = msg.getHttpResponse();
        if(response == null){
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, body);
        }else {
            response=new DefaultFullHttpResponse(msg.getHttpResponse()
                    .getProtocolVersion(),msg.getHttpResponse().getStatus(),
            body);
        }
        response.headers().set(CONTENT_TYPE,"text/xml");
        setContentLength(response,body.readableBytes());
        out.add(response);
    }
}
