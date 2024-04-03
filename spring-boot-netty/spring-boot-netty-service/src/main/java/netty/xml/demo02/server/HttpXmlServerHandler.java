package netty.xml.demo02.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import netty.xml.Order;
import netty.xml.demo02.req.HttpXmlRequest;
import netty.xml.demo02.resp.HttpXmlResponse;

import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:59
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
    //@Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, HttpXmlRequest httpXmlRequest) throws Exception {
        //可以看出服务端业务逻辑处理类接收到的已经是解码后的业务消息
        HttpRequest request=httpXmlRequest.getRequest();
        Order order=(Order) httpXmlRequest.getBody();
        System.out.println("Http server receive request : "+order);
        //修改order
        // doBusiness(order);
        //发送修改后的order 数据
        //  ChannelFuture future=
        channelHandlerContext.
                writeAndFlush(new HttpXmlResponse(null,order));/*.
                addListener(ChannelFutureListener.CLOSE);*/
        System.out.println("server  send data complete..");
        //if ()
    }

    private void doBusiness(Order order){
        order.getCustomer().setFirstName("张");
        order.getCustomer().setLastName("飞");
        List<String> midNames=new ArrayList<String >();
        midNames.add("关羽");
        midNames.add("刘备");
        order.getCustomer().setMiddleNames(midNames);

        order.getBilTo().setCity("荆州");
        order.getBilTo().setCountry("东汉末年");
        order.getBilTo().setState("war");
        order.getBilTo().setPostCode("654321");


        order.getShipTo().setCity("荆州");
        order.getShipTo().setCountry("东汉末年");
        order.getShipTo().setState("war");
        order.getShipTo().setPostCode("654321");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause){
        if (context.channel().isActive()){
            sendError(context, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private  void sendError(ChannelHandlerContext context, HttpResponseStatus status){
        //构造错误返回体
        FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("失败: "+ status +"\r\n",
                CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain; charset=UTF-8");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpXmlRequest httpXmlRequest) throws Exception {
        //可以看出服务端业务逻辑处理类接收到的已经是解码后的业务消息
        System.out.println("HTTP：请求消息来了");
        HttpRequest request=httpXmlRequest.getRequest();
        Order order=(Order) httpXmlRequest.getBody();
        System.out.println("Http server receive request : "+order);
        //修改order
        // doBusiness(order);
        //发送修改后的order 数据
        //  ChannelFuture future=
        channelHandlerContext.
                writeAndFlush(new HttpXmlResponse(null,order));/*.
                addListener(ChannelFutureListener.CLOSE);*/
        System.out.println("server  send data complete..");
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        HttpRequest request=httpXmlRequest.getRequest();
//        Order order=(Order) httpXmlRequest.getBody();
//        System.out.println("Http server receive request : "+order);
//        //修改order
//        // doBusiness(order);
//        //发送修改后的order 数据
//        //  ChannelFuture future=
//        channelHandlerContext.
//                writeAndFlush(new HttpXmlResponse(null,order));/*.
//                addListener(ChannelFutureListener.CLOSE);*/
//        System.out.println("server  send data complete..");
//    }
}
