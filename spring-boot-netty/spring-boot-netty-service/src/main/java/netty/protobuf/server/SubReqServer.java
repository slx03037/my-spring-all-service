package netty.protobuf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.protobuf.SubscribeReqProto;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 11:20
 */
public class SubReqServer {
    public void bind(int port) throws InterruptedException {
        //配置服务端的NIO线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try{
            //构建服务启动对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /**设置两个线程组bossGroup 和workGroup*/
            serverBootstrap.group(bossGroup,workGroup)
                    /**设置服务端的通道类型*/
                    .channel(NioServerSocketChannel.class)
                    /**设置线程队列的个数*/
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    /**使用匿名内部类的方式初始化通道对象*/
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            /***
                             * ChannelPipeline添加ProtobufVarint32FrameDecoder，它主要用于半包处处理
                             * 随后继续添加ProtobufDecoder解码器，它的参数是com.google.protobuf.MessageLite
                             * 实际上就是要告诉ProtobufDecoder需要解码的目标类是什么，否则仅仅从字节数组中无法判断处
                             * 要解码的目标类型信息
                             */
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            ch.pipeline().addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
                            ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            ch.pipeline().addLast(new ProtobufEncoder());
                            ch.pipeline().addLast(new SubReqServerHandler());
                        }
                    });
             /**绑定端口启动服务*/
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            /**对关闭端口进行监听*/
            sync.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new SubReqServer().bind(8080);
    }
}
