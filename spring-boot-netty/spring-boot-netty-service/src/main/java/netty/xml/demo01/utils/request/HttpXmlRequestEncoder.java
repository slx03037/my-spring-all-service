package netty.xml.demo01.utils.request;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import netty.xml.demo01.utils.code.AbstractHTTPXmlEncoder;

import java.net.InetAddress;
import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 14:33
 */
public class HttpXmlRequestEncoder extends AbstractHTTPXmlEncoder<HttpXmlRequest> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg.getBody());
        FullHttpRequest request=msg.getRequest();

        if(request ==null){
            /**
             * 构造和设置HTTP消息头，由于通常情况下HTTP通信双方更关注消息体本身，所以这里采用了硬编码得方式，
             * 如果要产品化，可以做成xml配置文件运行业务自定义配置，以提升定制得灵活性
             */
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
            HttpHeaders headers = request.headers();
            headers.set(HttpHeaders.Names.HOST
                    , InetAddress.getLocalHost().getHostAddress());
            headers.set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.CLOSE);
            headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP +","
                    + HttpHeaders.Values.DEFLATE);
            headers.set(HttpHeaders.Names.ACCEPT_CHARSET,"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE,"zh");
            headers.set(HttpHeaders.Names.USER_AGENT,"Netty xml Http Client side");
            headers.set(HttpHeaders.Names.ACCEPT
                    ,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        }
        /***由于消息体不为空，也没有使用Chunk方式，所以在消息头中设置消息体得长度Content-length，
         * 完成消息体得XML序列化后将重新构造HTTP得请求消息加入到out中*/
        HttpHeaders.setContentLength(request,body.readableBytes());
        out.add(request);
    }

}
