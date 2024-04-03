package netty.agreement.exmaple.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.MessageType;
import netty.agreement.datastructure.NettyMessage;

/**
 * @author shenlx
 * @description 握手请求
 * @date 2024/4/1 10:11
 */
@Slf4j
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {

    /**
     * 连接建立时，发起认证
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活，握手请求认证..................");

        ctx.writeAndFlush(buildLoginReq());
    }


    /**
     * 收到服务端消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.HANDSHAKE_REQUEST.getType()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 握手失败，关闭连接
                ctx.close();
            } else {
                log.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else
            //调用下一个channel链..
        {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 构建登录请求
     */
    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HANDSHAKE_REQUEST.getType().byteValue());
        message.setHeader(header);
        return message;
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
