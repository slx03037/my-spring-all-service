package netty.xml.demo02.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.xml.OrderFactory;
import netty.xml.demo02.req.HttpXmlRequest;
import netty.xml.demo02.resp.HttpXmlResponse;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:57
 */
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {
    @Override
    public void channelActive(ChannelHandlerContext context){
        //链接成功的时候，给服务端发送HttpXmlRequest对象
        //编码器会自动完成编码
        HttpXmlRequest request=new HttpXmlRequest(null, OrderFactory.create(123));
        System.out.println("发送请求消息:"+request);
        context.writeAndFlush(request);
    }


    //@Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlResponse response) throws Exception {
        //此时接收到的信息 已经是自动解码后的HttpXmlResponse对象了
        System.out.println("The client receive response of http header is : "+response.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : "+response.getResult());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpXmlResponse response) throws Exception {
        //此时接收到的信息 已经是自动解码后的HttpXmlResponse对象了
        System.out.println("The client receive response of http header is : "+response.getHttpResponse().headers().names());
        System.out.println("The client receive response of http body is : "+response.getResult());
    }
}
