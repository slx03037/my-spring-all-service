package netty.agreement.heartbeat.resp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.NettyMessage;
import netty.agreement.loginAuth.MessageType;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 22:14
 */
@Slf4j
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 接收到心跳请求消息之后，构造心跳应答消息返回，并打印接收和发送的心跳消息
         */
        NettyMessage message = (NettyMessage) msg;
        //返回心跳应答消息
        if(message.getHeader() !=null
                && message.getHeader().getType()== MessageType.HEARTBEAT_REQ.getValue()){
           log.info("Receive client heart beat message:--->:{}",message);
            NettyMessage heartBeat = buildHeartBeat();
            log.info("send heart beat response message to client:====>:{}",heartBeat);
            ctx.writeAndFlush(heartBeat);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 心跳超时的实现，直接利用Netty的ReadTimoutHandler机制，当一定周期内(默认值50s)没有读取到对应任何消息时，需要主动关闭链路
     * 如果客户端，重新发起连接，如果时服务端，释放在原，清楚客户端登录缓存消息，等到服务端重连
     * @return
     */

    private NettyMessage buildHeartBeat(){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.HEARTBEAT_RESP.getValue());
        message.setHeader(header);
        return message;
    }

}
