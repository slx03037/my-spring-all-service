package netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpUtil.setContentLength;

/**
 * @author shenlx
 * @description
 * @date 2024/3/4 14:03
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler {
    private WebSocketServerHandshaker handshaker;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //传统的http接入
        /**
         *第一次握手消息由HTTP协议承载，所以它是一个HTTP消息，
         *
         */
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx,(FullHttpRequest) msg);

        }
        //WebSocket接入
        else if(msg instanceof WebSocketFrame){
        handleWebSocketFrame(ctx,(WebSocketFrame) msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req){
        /**
         * 执行handleHttpRequest方法来处理WebSocket握手消息，首先对握手请求消息进行判断，如果消息头中没有包含Upgrade字段或者它的之不是websocket，则返回HTTP400响应
         */
        //如果HTTP解码失败，返回HTTP异常
        if(!req.getDecoderResult().isSuccess()
                || (!"websocket".endsWith(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //构造握手响应返回，本机测试
        /**
         * 握手请求简单校验通过只会，开始构造握手工厂，创建握手处理类WebSocketServerHandshaker，
         * 通过它构造握手响应消息返回给客户端，同时将WebSocket相关的编码和解码类动态添加到ChannelPipeline中
         * 用于WebSocket对象进行操作
         */
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory(
                "ws//localhost:8080/websocket", null, false);
         handshaker = webSocketServerHandshakerFactory.newHandshaker(req);
        if(handshaker ==null){
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(),req);
        }

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        //判断是否是关闭链路的指令
        /**
         * 首先对控制帧进行判断，如果是关闭链路的控制消息，就调用WebSocketServerHandshaker的close方法关闭WebSocket连接，如果是维持链路的Ping消息，则构造Pong消息返回，
         */
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(),(CloseWebSocketFrame) frame.retain());
            return;
        }

        //判断是否是ping消息
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //本例程仅支持文本消息，不支持二进制消息
        if(!(frame instanceof  TextWebSocketFrame)){
            throw  new UnsupportedOperationException(String.format("%s frame types not supported",frame.getClass().getName()));
        }

        //返回应答消息
        /**
         * 从TextWebSocketFrame中获取请求消息字符串，对它处理后通过构造信的TextWebSocketFrame消息返回给客户端，由于握手应答时动态增加了TTextWebSocketFrame的编码类
         * 所以可以直接发送TextWebSocketFrame对象
         */
        String text = ((TextWebSocketFrame) frame).text();
        System.out.println(ctx.channel() +"receive"+text);
        ctx.channel().write(new TextWebSocketFrame(text+",欢迎使用Netty WebSocket服务，现在时刻:"+ new Date()));
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,FullHttpResponse resp){
        //返回应答给客户端
        if(resp.getStatus().code() !=200){
            ByteBuf byteBuf = Unpooled.copiedBuffer(resp.getClass().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(byteBuf);
            byteBuf.release();
            setContentLength(resp,resp.content().readableBytes());
        }
        //如果是非keep-alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(resp);
        if(!isKeepAlive(req) || resp.getStatus().code() !=200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
        cause.printStackTrace();
        context.close();
    }
}
