package netty.xml.demo01.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import netty.xml.Address;
import netty.xml.Order;
import netty.xml.demo01.utils.request.HttpXmlRequest;
import netty.xml.demo01.utils.response.HttpXmlResponse;

import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 22:07
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
        HttpRequest request = msg.getRequest();

        Order body = (Order) msg.getBody();

        System.out.println("Http,server receive request:"+body);

        dobusiness(body);
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, body));
        if(!isKeepAlive(request)){
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    ctx.close();
                }
            });
        }
    }

    private void dobusiness(Order order){
        order.getCustomer().setFirstName("狄");
        order.getCustomer().setLastName("仁杰");
        List <String> midNames=new ArrayList<>();
        midNames.add("李元芳");
        order.getCustomer().setMiddleNames(midNames);
        Address address = order.getBilTo();
        address.setCity("洛阳");
        address.setCountry("大唐");
        address.setState("河南道");
        address.setPostCode("123456");
        order.setBilTo(address);
        order.setShipTo(address);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if(ctx.channel().isActive()){
            sendError(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status
                , Unpooled.copiedBuffer("失败：" + status + "\r\n", CharsetUtil.UTF_8));
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
