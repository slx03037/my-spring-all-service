package netty.agreement.loginAuth.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.loginAuth.MessageType;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.NettyMessage;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 15:42
 */

/**
 * 握手的发起是在客户端和服务端TCP链路建立成功通道激活时，握手消息的接入和安全认证在服务端处理
 */
@Slf4j
public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 客户端构造握手请求消息发送给服务端，由于采用IP白名单认证机制，因此不需要携带消息体
         *，消息体为空，消息类型为"3:握手请求消息"。握手请求发送之后，按照协议规范，服务端不需要返回握手应答消息
         */
        ctx.writeAndFlush(buildLoginReq());
    }

    /**
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 对握手应答消息进行处理，首先判断消息是否是握手应答消息，如果不是，直接透传给后面的ChannelHandler
         * 进行处理，如果是握手应答消息，则对应答结果进行判断，如果非0，说明认证失败，关闭链路，重新发起连接
         */
        NettyMessage message=(NettyMessage) msg;
        //如果是握手应答消息，需要判断是否认证成功
        if(message.getHeader() !=null
                && message.getHeader().getType() == MessageType.LOGIN_RESPONSE.getValue()){
            byte loginResult = (byte) message.getBody();
            if(loginResult !=(byte)0){
                //握手失败，关闭连接
                ctx.close();
            }else{
                log.info("Login is ok :{}",message);
                ctx.fireChannelRead(msg);
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq(){
        NettyMessage message=new NettyMessage();
        Header header =new Header();
        header.setType(MessageType.LOGIN_RESPONSE.getValue());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }
}
