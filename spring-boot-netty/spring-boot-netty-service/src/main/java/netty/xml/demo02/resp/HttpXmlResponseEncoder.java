package netty.xml.demo02.resp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import netty.xml.demo02.untils.AbstractHttpXmlEncoder;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:54
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
    @Override
    protected void encode(ChannelHandlerContext context,
                          HttpXmlResponse responseMsg, List<Object> list) throws Exception {
        ByteBuf body = encode0(context, responseMsg.getResult());
        FullHttpResponse response = responseMsg.getHttpResponse();
        if (response == null) {
            //如果业务侧没有构造HttpResponse,则构造一个
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        } else {
            //如果业务侧已经构造了一个，但是由于 Netty的DefaultFullResponse 没有提供动态设置消息体content的接口。
            //只能在第一次构造的时候设置content。
            // 所以现在为了添加内容，只能是舍弃原先的FullHttpResponse,重新再构造一个。
            response = new DefaultFullHttpResponse(responseMsg.getHttpResponse().getProtocolVersion(),
                    responseMsg.getHttpResponse().getStatus(), body);
        }
        //消息头设置消息体内容格式
        response.headers().set(CONTENT_TYPE,"text/html");
        //消息头中设置消息体的长度
        setContentLength(response,body.readableBytes());
        //将编码后的DefaultFullHttpResponse 对象添加到编码结果列表中，
        //由后续Netty的HTTP编码类进行二次编码
        list.add(response);
    }
}
