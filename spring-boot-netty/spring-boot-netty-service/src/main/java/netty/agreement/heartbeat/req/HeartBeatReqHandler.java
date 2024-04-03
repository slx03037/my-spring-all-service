package netty.agreement.heartbeat.req;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.NettyMessage;
import netty.agreement.loginAuth.MessageType;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 20:49
 */
@Slf4j
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        /**
         * 当我收成功之后，握手请求Handler会继续将握手成功消息向下透传，HeartBeatReqHandler接收到之后对消息进行判断
         * ，如果是握手成功消息，则启动无限循环定时器用于定期发送心跳消息，由于NioEventLoop是一个Schedule，因此它支持定时器
         * 的执行，心跳定时器的单位是毫秒，默认为5000，即每5秒发送一条心跳消息
         */
        //握手成功，主动发送心跳消息
        if(message.getHeader() !=null
                && message.getHeader().getType()== MessageType.LOGIN_RESPONSE.getValue()){
            heartBeat=ctx.executor().scheduleAtFixedRate(
                    new HeartBeatReqHandler.HeartBeatTask(ctx),0,5000, TimeUnit.MICROSECONDS
            );
            /**
             * 为了统一在一个Handler中处理所有的心跳消息，因此下面的代码用于接收服务端发送的心跳应答消息，并打印客户端接收和发送
             * 心跳消息
             */
        }else if (message.getHeader() !=null && message.getHeader().getType()==MessageType.HEARTBEAT_RESP.getValue()){
            log.info("Client receive server heart beat message :---->{}"+message);
        }else {
            ctx.fireChannelRead(msg);
        }

    }

    private class HeartBeatTask implements Runnable{
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(final ChannelHandlerContext ctx){
            this.ctx=ctx;
        }


        @Override
        public void run() {
            NettyMessage heartBeat=buildHeatBeat();
            log.info("client sned heart beat message to sever:---->:{}",heartBeat);
            ctx.writeAndFlush(heartBeat);
        }

        private NettyMessage buildHeatBeat(){
            NettyMessage message=new NettyMessage();
            Header header=new Header();
            header.setType(MessageType.HEARTBEAT_REQ.getValue());
            message.setHeader(header);
            return message;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        if(heartBeat !=null){
            heartBeat.cancel(true);
            heartBeat=null;
        }
        ctx.fireExceptionCaught(cause);
    }


}
