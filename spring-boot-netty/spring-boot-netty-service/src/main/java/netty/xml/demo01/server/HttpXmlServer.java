package netty.xml.demo01.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import netty.xml.Order;
import netty.xml.demo01.utils.request.HttpXmlRequestDecoder;
import netty.xml.demo01.utils.response.HttpXmlResponseEncoder;

import java.net.InetSocketAddress;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 22:00
 */
public class HttpXmlServer {
    public void run(final int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("xml-decoder",new HttpXmlRequestDecoder(Order.class,true));
                            ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                            ch.pipeline().addLast("xml-encoder",new HttpXmlResponseEncoder());
                            ch.pipeline().addLast("xmlServerHandler",new HttpXmlServerHandler());
                        }
                    });
            ChannelFuture sync = b.bind(new InetSocketAddress(port)).sync();
            sync.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[]args) throws InterruptedException {
        new HttpXmlServer().run(8080);
    }
}
