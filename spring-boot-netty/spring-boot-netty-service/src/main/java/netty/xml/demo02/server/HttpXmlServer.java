package netty.xml.demo02.server;

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
import netty.xml.demo02.resp.HttpXmlResponseEncoder;
import netty.xml.demo02.req.HttpXmlRequestDecoder;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:58
 */
public class HttpXmlServer {
    /**
     * 接收HTTP客户端的连接
     * 接收HTTP客户端的XML请求消息，并将其解码为Order对象
     * 对Order对象进行业务处理，构造应答消息返回
     * 通过HTTP+XML的格式返回应答消息
     * 主动关闭HTTP连接
     */
    public void run(int port){
        EventLoopGroup boosGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 首先添加HTTP请求消息解码器，之后添加我们自定义的HttpXmlRequestDecoder，之后添加自定义的HttpXmlResponseEncoder编码器用于响应消息的编码。
                             */
                            //HttpRequestDecoder 负责将二进制码流解码成为HTTP的请求消息
                            socketChannel.pipeline()
                                    .addLast("http-decoder",new HttpRequestDecoder());
                            //HttpObjectAggregator 负责将一个HTTP请求消息的多个部分合并成一条完整的HTTP信息
                            socketChannel.pipeline()
                                    .addLast("http-aggregator",new HttpObjectAggregator(65536));
                            //xml解码器，实现HTTP+XML请求消息的自动解码
                            socketChannel.pipeline()
                                    .addLast("xml-decoder",new HttpXmlRequestDecoder(Order.class,true));
                            //HttpResponseEncoder 将应答消息编码为HTTP消息
                            socketChannel.pipeline()
                                    .addLast("http-encoder",new HttpResponseEncoder());
                            //HttpXmlResponseEncoder 将HTTP 实现HTTP+XML请求消息的自动编码
                            socketChannel.pipeline()
                                    .addLast("xml-encoder",new HttpXmlResponseEncoder());
                            //业务逻辑处理类
                            socketChannel.pipeline()
                                    .addLast(new HttpXmlServerHandler());
                        }
                    });
            ChannelFuture future=bootstrap.bind(port).sync();
            System.out.println("HTTP-XML Server is started,waiting for client to connect..");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new HttpXmlServer().run(8080);
    }
}
