package netty.marshalling.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.marshalling.MarshallingCodeCfactory;
import netty.protobuf.server.SubReqServerHandler;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 13:55
 */
public class SubReqServer {
    public void bind(int port) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(MarshallingCodeCfactory.buildMarshallingDecoder());
                            ch.pipeline().addLast(MarshallingCodeCfactory.buildMarshallingEncoder());
                            //ch.pipeline().addLast(new SubReqServerHandler());
                            ch.pipeline().addLast(new netty.protobuf.server.SubReqServerHandler());
                        }
                    });

            ChannelFuture sync = serverBootstrap.bind(port).sync();

            sync.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new SubReqServer().bind(8080);
    }
}
