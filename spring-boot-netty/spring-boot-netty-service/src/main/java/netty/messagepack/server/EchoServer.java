package netty.messagepack.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import netty.io.server.TimeServer;
import netty.messagepack.MsgpackDecoder;
import netty.messagepack.MsgpackEncoder;
import netty.tcp.Unpacking.server.EchoFixedServer;

/**
 * @author shenlx
 * @description
 * @date 2024/2/26 17:07
 */
public class EchoServer {
    public void bind(int port) throws InterruptedException {
        /**配置服务端的NIO线程*/
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            /**创建服务端的启动对象，设置参数*/
            ServerBootstrap b = new ServerBootstrap();
            /**设置两个线程组bossGroup和workGroup*/
            b.group(bossGroup,workGroup)
                    /**设置服务端通道实现类型*/
                    .channel(NioServerSocketChannel.class)
                    /**设置线程队列的连接个数*/
                    .option(ChannelOption.SO_BACKLOG,100)
                    /**设置日志打印等级*/
                    .handler(new LoggingHandler(LogLevel.INFO))
                    /**使用匿名内部类的形式初始化通道对象*/
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder"
                                    ,new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
                            ch.pipeline().addLast("msgpack decoder",new MsgpackDecoder());
                            ch.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
                            //ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new EchoSeverHandler());
                        }
                    });

            /**绑定端口号启动服务*/
            ChannelFuture f = b.bind(port).sync();

            /**对关闭端口号进行监听*/
            f.channel().closeFuture().sync();
        }finally {
            /**优雅退出，释放线程池资源*/
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new EchoServer().bind(8080);
    }
}
