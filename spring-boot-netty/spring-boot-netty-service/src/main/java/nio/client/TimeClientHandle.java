package nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 16:43
 */
public class TimeClientHandle implements Runnable{
    private final String host;

    private final int port;

    private Selector selector;

    private SocketChannel socketChannel;

    private volatile  boolean stop;

    /**
     * 构造函数用于初始化NIO的多路复用器和SocketChannel对象，需要注意的是，创建socketChannel之后，需要将其设置位异步非阻塞模式
     */
    public TimeClientHandle(String host,int port){
        this.host=host==null?"127.0.0.1":host;
        this.port=port;
        try{
            selector=Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 用于发送连接请求，作为示例，连接成功，所以不需要做重连操作，因此将其放到循环之前，
     */
    @Override
    public void run() {
        try{
            doConnect();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * 在循环体中轮询多路复用器Selector，当有就绪的Channel时，执行handleInput(key)方法
         */
        while(!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key=null;
                while(iterator.hasNext()){
                    key=iterator.next();
                    iterator.remove();
                    try{
                        handleInput(key);
                    }catch (Exception el){
                        if(key !=null){
                            key.cancel();
                            if(key.channel() !=null){
                                key.channel().close();
                            }
                        }
                    }
                }

            }catch (IOException el){
                el.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     * 判断SelectionKey的状态，如果时处于连接状态，说明服务端已经返回ack应答消息。这时我们需要对连接结果进行判断，调用SocketChannel
     * 的finishConnect()方法，如果返回为true，说明客户端连接成功；
     * 如果返回为false或者直接抛出IOException，说明连接失败
     */
    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //判断是否连接成功
            SocketChannel channel = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(channel.finishConnect()){
                    /**
                     * 连接成功，将SocketChannel注册到多路复用器上，注册SelectionKey.OP_READ操作位，监听网络读操作，然后发送请求消息给客户端
                     */
                    channel.register(selector,SelectionKey.OP_READ);
                    dowrite(channel);
                }else {
                    //连接失败，进程退出
                    System.exit(1);
                }
            }

            if(key.isReadable()){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int reads = channel.read(buffer);
                if(reads > 0){
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("NOW is:"+body);
                    this.stop=true;
                }else if(reads < 0 ){
                    //对端链路关闭
                    key.cancel();
                    channel.close();
                }else{
                    System.out.println("没有读取到字节，属于正常场景，忽悠");
                }
            }
        }
    }

    /**
     * 首先对SocketChannel的connect()操作进行判断，如果连接成功，则将SocketChannel注册到多路复用器Selector上，注册SelectionKey.OP_READ；
     * 如果没有直接连接成功，则说明服务端没有返回TCP握手应答消息，但这个并不代表连接失败。我们需要将SocketChannel注册到多路复用器Selector上，注册
     * SelectionKey.OP_CONNECT,当服务端返回TCP syn—ack消息后，Selector就能够轮询到这个SocketChannel处于连接就绪状态
     */
    private void doConnect() throws IOException {
        //如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    /**
     * 构造请求消息体，然后对其编码，写入到发送缓冲区中，最后调用SocketChannel的write方法进行发送，由于发送是异步的，所有会存在"半包写"问题，
     * 最后通过hasRemaining()方法对发送结果进行判断，如果缓冲区中的消息全部发送完成，打印“send order 2 server succeed”
     */
    private void dowrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if(!writeBuffer.hasRemaining()){
            System.out.println("send order 2 server succeed");
        }

    }


}
