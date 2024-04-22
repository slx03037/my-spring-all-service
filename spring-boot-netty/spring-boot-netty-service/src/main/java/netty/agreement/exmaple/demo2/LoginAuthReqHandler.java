package netty.agreement.exmaple.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.agreement.loginAuth.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenlx
 * @description
 * @date 2024/4/3 15:34
 */
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthReqHandler.class);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("通道激活，握手请求认证..................");

        ctx.writeAndFlush(buildLoginReq());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;

        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {

            byte loginResult = (byte) message.getBody();

            if (loginResult != ResultType.SUCCESS.value()) {
                ctx.close();
            } else {
                System.out.println("Login is OK ： " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
