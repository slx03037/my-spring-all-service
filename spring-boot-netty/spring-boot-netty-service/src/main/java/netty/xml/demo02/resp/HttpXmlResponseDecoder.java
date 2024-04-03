package netty.xml.demo02.resp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import netty.xml.demo02.untils.AbstractHttpXmlDecoder;

import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:54
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {
    public HttpXmlResponseDecoder(Class<?> clazz){
        this(clazz,false);
    }

    public HttpXmlResponseDecoder(Class<?> clazz,boolean isPrint){
        super(clazz,isPrint);
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          DefaultFullHttpResponse defaultFullHttpResponse,
                          List<Object> list) throws Exception {
        //1.利用基类的decode0方法 解码响应信息
        //2.构造HttpXmlResponse 对象
        //3.添加到解码结果列表中。供后续继续解码
        HttpXmlResponse response =new HttpXmlResponse(defaultFullHttpResponse,
                decode0(channelHandlerContext,defaultFullHttpResponse.content()));
        list.add(response);
    }
}
