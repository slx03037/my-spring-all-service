package netty.xml.demo02.req;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import netty.xml.demo02.untils.AbstractHttpXmlDecoder;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:52
 */
public class HttpXmlRequestDecoder extends AbstractHttpXmlDecoder<FullHttpRequest> {
    public HttpXmlRequestDecoder(Class<?> clazz) {
        this(clazz,false);
    }

    public HttpXmlRequestDecoder(Class<?> clazz,boolean isprint){
        super(clazz,isprint);
    }



    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, FullHttpRequest request,
                          List<Object> list) throws Exception {
        //如果解码失败，返回400
        if (!request.getDecoderResult().isSuccess()){
            sendError(channelHandlerContext, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        //通过反序列化得到的ByteBuf来构造 HttpXmlRequest对象。
        HttpXmlRequest request1=new HttpXmlRequest(request,
                //反序列化Request 里面的内容。
                decode0(channelHandlerContext,request.content()));
        //添加到解码结果list列表
        list.add(request1);
    }


    private static void sendError(ChannelHandlerContext context, HttpResponseStatus status){
        //构造错误返回体
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: "+ status +"\r\n",
                CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain; charset=UTF-8");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
