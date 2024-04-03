package netty.xml.demo01.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import netty.xml.Order;
import netty.xml.demo01.utils.request.HttpXmlRequestEncoder;
import netty.xml.demo01.utils.response.HttpXmlResponseDecoder;

import java.net.InetSocketAddress;

/**
 * @author shenlx
 * @description
 * @date 2024/2/29 21:38
 */
public class HttpXmlClient {
    public void connect(int port) throws InterruptedException {
        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
                            //xml解码器
                            ch.pipeline().addLast("xml-decoder",new HttpXmlResponseDecoder(Order.class,true));
                            ch.pipeline().addLast("http-encoder",new HttpRequestEncoder());
                            ch.pipeline().addLast("xml-encoder",new HttpXmlRequestEncoder());
                            ch.pipeline().addLast("xmlClientHandler",new HttpXmlClientHandle());
                        }
                    });
            //发起异步连接操作
            ChannelFuture sync = b.connect(new InetSocketAddress(port)).sync();
            sync.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[]args) throws InterruptedException {
        new HttpXmlClient().connect(8080);
    }
}
