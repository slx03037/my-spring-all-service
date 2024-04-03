package netty.agreement.exmaple.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.MessageType;
import netty.agreement.datastructure.NettyMessage;

/**
 * @author shenlx
 * @description 心跳响应(服务端处理)
 * @date 2024/4/1 15:35
 */
@Slf4j
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.HEARTBEAT_REQUEST.getType()) {
            log.info("Receive client heart beat message : ---> "
                    + message);
            NettyMessage heartBeat = buildHeatBeat();
            log.info("Send heart beat response message to client : ---> "
                    + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 返回心跳响应
     * @return
     */
    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_REQUEST.getType().byteValue());
        message.setHeader(header);
        return message;
    }
}
