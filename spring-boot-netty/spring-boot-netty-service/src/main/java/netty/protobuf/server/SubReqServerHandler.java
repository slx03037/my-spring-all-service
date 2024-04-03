package netty.protobuf.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.protobuf.SubscribeReqProto;
import netty.protobuf.SubscribeRespProto;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 11:46
 */
@ChannelHandler.Sharable
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /***
         * 由于ProtobufDecoder已经对消息进行了自动解码，因此接收到的订购请求消息可以直接使用，
         * 对于用户进行校验，校验通过后构造应答消息返回给客户端，由于使用了ProtobufEncoder，
         * 所有不需要对SubscribeRespProto.SubscribeResp进行手工编码
         */
        SubscribeReqProto.SubscribeReq req=(SubscribeReqProto.SubscribeReq)msg;
        if("Lll".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accpet client subscribe req:{" + req +"}");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqId){
        SubscribeRespProto.SubscribeResp.Builder builder= SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqId);
        builder.setRespCode(200);
        builder.setDesc("Netty book order succeed,3 days later ,sent to the designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
