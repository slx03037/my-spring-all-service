package netty.marshalling.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.marshalling.ReqData;
import netty.marshalling.RespData;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 14:19
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
        ReqData req=(ReqData)msg;
        if("lisi".equalsIgnoreCase(req.getName())){
            System.out.println("Service accpet client subscribe req:{" + req +"}");
            ctx.writeAndFlush(resp(req.getId()));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("--------服务端数据读取完毕---------");
    }

    private RespData resp(String id){
        RespData respData=new RespData();
        respData.setId(id);
        respData.setName("张三");
        respData.setAge(10);

        return respData;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
}
