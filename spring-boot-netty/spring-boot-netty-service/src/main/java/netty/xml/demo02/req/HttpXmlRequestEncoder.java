package netty.xml.demo02.req;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import netty.xml.demo02.untils.AbstractHttpXmlEncoder;

import java.net.InetAddress;
import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:44
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext,
                          HttpXmlRequest httpXmlRequest, List<Object> list) throws Exception {
        //调用父类的encode0,将业务需要发生的POJO对象Order实例通过 JiBx 序列化为XML,
        //随后转换为Netty的ByteBuf对象
        ByteBuf body = encode0(channelHandlerContext, httpXmlRequest.getBody());
        FullHttpRequest request = httpXmlRequest.getRequest();
        //request 如果为空,初始化一个request,并设置请求头
        if (request == null) {
            //此处采用了硬编码方式，如果要做成产品话，可以做成XML配置文件，
            // 允许业务自定义配置，提升定制的灵活性
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
            HttpHeaders httpHeaders = request.headers();
            //设置host
            httpHeaders.set(HttpHeaders.Names.HOST, InetAddress.getLocalHost());
            //设置客户端可接受的内容编码
            httpHeaders.set(HttpHeaders.Names.ACCEPT_ENCODING,
                    HttpHeaders.Values.GZIP + "," + HttpHeaders.Values.DEFLATE);
            //设置客户端可接受的字符集
            httpHeaders.set(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
            //设置客户端可接受的语言
            httpHeaders.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "zh");
            //设置 客户端的操作系统
            httpHeaders.set(HttpHeaders.Names.USER_AGENT, "Netty xml Http Client side");
            //设置客户端接收哪些类型
            httpHeaders.set(HttpHeaders.Names.ACCEPT,
                    "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        }
        //设置请求字节长度
        //由于请求消息消息体不为空，也没有使用Chunk方式，所以在HTTP消息头中设置消息体的长度Content-Length.
        HttpHeaders.setContentLength(request, body.readableBytes());
        //完成消息体的XML序列化后将重新构造的HTTP请求消息加入到list,
        // 由后续Netty的HTTP请求编码器继续对HTTP请求消息进行编码
        list.add(request);
    }
}
