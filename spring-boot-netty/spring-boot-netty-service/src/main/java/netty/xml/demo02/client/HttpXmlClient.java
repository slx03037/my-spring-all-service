package netty.xml.demo02.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import netty.xml.Order;
import netty.xml.demo02.req.HttpXmlRequestEncoder;
import netty.xml.demo02.resp.HttpXmlResponseDecoder;

/**
 * @author shenlx
 * @description
 * @date 2024/3/1 13:56
 */
public class HttpXmlClient {
    /**
     * 发起HTTP连接请求
     * 构造订购请求消息，将其编码成XML，通过HTTP协议发送为服务端
     * 接收服务端的应答消息，将XML应答消息反序列化为订购消息Order对象
     * 关闭HTTP连接
     */
    public void connect(String host,int port){
        EventLoopGroup loopGroup=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(loopGroup)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 在ChannelPipeline中新增了HTTPResponseDecoder，它负责将二进制码流解码成HTTP的应答消息。
                             * 新增的HttpObjectAggregator，它负责将一个HTTP的请求消息的多个部分合并成为一个完整的HTTP消息。
                             * 新增HttpXmlResponseDecoder，它负责将XML解码为Order对象，实现HTTP+XML应答消息的自动解码。
                             * 新增HttpRequestEncoder编码器，需要注意顺序，编码的时候是按照从尾到头的顺序调度执行的，之后是我们自定义开发的HTTP+XML请求消息编码器HttpXmlRequestEncoder，
                             * 最后是业务的逻辑编排类 HttpXmlClientHandler。
                             */
                            // HttpRequestEncoder 负责将二进制码流解码成为HTTP的应答消息
                            socketChannel.pipeline().
                                    addLast("http-decoder",new HttpResponseDecoder());
                            //HttpObjectAggregator 负责将一个HTTP请求消息的多个部分合并成一条完整的HTTP信息
                            socketChannel.pipeline()
                                    .addLast("http-aggregator",new HttpObjectAggregator(65536));
                            //xml解码器，实现HTTP+XML应答消息的自动解码
                            socketChannel.pipeline()
                                    .addLast("xml-decoder",new HttpXmlResponseDecoder(Order.class,true));
                            // HttpRequestEncoder 将POJO对象 编码为 HTTP接收的消息
                            socketChannel.pipeline()
                                    .addLast("http-encoder",new HttpRequestEncoder());
                            //HttpXmlRequestEncoder xml 编码器，实现HTTP-XML 请求消息的自动编码
                            socketChannel.pipeline()
                                    .addLast("xml-encoder",new HttpXmlRequestEncoder());
                            //自定义的业务逻辑处理类
                            socketChannel.pipeline()
                                    .addLast(new HttpXmlClientHandler());
                        }
                    });
            //发起异步链接操作
            ChannelFuture future=bootstrap.connect(host,port).sync();
            System.out.println("HTTP-XML client is stated..... and do connect with server ");
            //等待客户端链路关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //优雅退出，释放NIO线程组.
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        int port=8080;
        new HttpXmlClient().connect("127.0.0.1",port);
    }
}
