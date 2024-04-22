package netty.agreement.exmaple.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author shenlx
 * @description
 * @date 2024/4/3 14:56
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {

    private final static Log LOG = LogFactory.getLog(LoginAuthRespHandler.class);

    /**
     * ip本地缓存
     */
    private final Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();

    /**
     * 白名单
     */
    private final String[] whitekList = {"127.0.0.1", "192.168.1.104"};


    /**
     * 读取到客服端消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 如果是握手请求消息，处理，其它消息透传
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HANDSHAKE_REQUEST.getType()) {
            // 获取远程IP地址
            String nodeIndex = ctx.channel().remoteAddress().toString();
            // 响应消息
            NettyMessage loginResp = null;
            // 重复登陆，拒绝
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                // 判断是否是白名单
                boolean isOK = false;
                for (String WIP : whitekList) {
                    if (WIP.equals(ip)) {
                        isOK = true;
                        break;
                    }
                }
                // 登录响应消息
                loginResp = isOK ? buildResponse((byte) 0)
                        : buildResponse((byte) -1);
                if (isOK)
                    nodeCheck.put(nodeIndex, true);
            }
            LOG.info("The login response is : " + loginResp
                    + " body [" + loginResp.getBody() + "]");
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 构建响应报文
     * @param result
     * @return
     */
    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Head header = new Head();
        header.setType(MessageType.HANDSHAKE_RESPONSE.getType().byteValue());
        message.setHeader(header);
        message.setBody(result);
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
        cause.printStackTrace();
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}
