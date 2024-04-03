package netty.agreement.loginAuth.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import netty.agreement.loginAuth.MessageType;
import netty.agreement.datastructure.Header;
import netty.agreement.datastructure.NettyMessage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shenlx
 * @description
 * @date 2024/4/1 17:39
 */
@Slf4j
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    /**
     * 重复登录保护和IP认证白名单列表，主要用于提升握手的可靠性
     */

    /**
     * 重复登录保护
     */
    private final Map<String,Boolean> nodeCheck=new ConcurrentHashMap<String,Boolean>();
    /**
     * ip认证白名单列表
     */
    private final String[] whiteList={"127.0.0.1"};

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 用于接入认证，首先根据客户端的源地址(/127.0.0.1:12088)进行重复登录判断，如果客户端以及登录成功，拒绝重复登录，以防止由于
         * 客户端重复登录导致的句柄泄露，随后通过ChannelHandlerContext的channel接口获取客户端的InetSocketAddress地址
         * ，从中取得发送方的源地址信息，通过源地址进行白名单校验，校验通过握手成功，否则握手失败
         * ，最好通过buildResponse构造握手应答消息返回给客户端
         */
        NettyMessage nettyMessage = (NettyMessage) msg;

        //如果是握手请求消息，处理，其他消息透传
        if(nettyMessage.getHeader() !=null
                && nettyMessage.getHeader().getType() == MessageType.LOGIN_RESPONSE.getValue()){
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp=null;
            //重复登录，拒绝
            if(nodeCheck.containsKey(nodeIndex)){
                loginResp=buildResponse((byte)-1);
            }else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                boolean isOk=false;
                for(String wip:whiteList){
                    if(wip.equals(ip)){
                        isOk=true;
                        break;
                    }
                }
                loginResp=isOk?buildResponse((byte)0):buildResponse((byte)-1);
                if(isOk){
                    nodeCheck.put(nodeIndex,true);
                }
                log.info("The login response is:{},body[{}];",loginResp,loginResp.getBody());
                ctx.writeAndFlush(loginResp);
            }
        }else {
            ctx.fireChannelRead(msg);
        }

    }

    private NettyMessage buildResponse(byte result){
        NettyMessage message=new NettyMessage();
        Header header=new Header();
        header.setType(MessageType.LOGIN_RESPONSE.getValue());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        nodeCheck.remove(ctx.channel().remoteAddress().toString());//删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

}
