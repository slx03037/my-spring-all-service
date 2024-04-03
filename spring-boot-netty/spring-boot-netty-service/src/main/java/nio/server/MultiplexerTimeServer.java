package nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 15:14
 */
public class MultiplexerTimeServer implements Runnable{
    private Selector selector;

    private ServerSocketChannel socketChannel;

    private volatile boolean stop;

    /**
     *在构造方法中进行资源初始化，创建多路复用器Selector、ServerSocketChannel,对Channel和TCP参数进行配置。
     * 例如，将ServerSocketChannel设置为异步非阻塞模式，它的backlog设置1024。系统资源初始化成功后，将ServerSocketChannel注册到
     * Selector，监听SelectionKey.OP_ACCEPT操作位，如果资源初始化失败(例如端口被占用)，则退出。
     */
    public MultiplexerTimeServer(int port){
        try{
            selector = Selector.open();
            socketChannel=ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(port),1024);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:"+port);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void stop(){
        this.stop=true;
    }

    /**
     * 在线程的run方法的while循环体中遍历selector，它的休眠事件位1s。无论是否有读写等事件发送，selector每隔1s都被唤醒一次。
     * selector也提供了一个无参的select方法；当有处于就绪的状态的Channel时，selector将返回该Channel的selectionKey集合，通过
     * 对就绪状态的Channel集合进行迭代，可以进行网络的异步读写操作
     */
    @Override
    public void run() {
        while(!stop){
            try{
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it= selectionKeys.iterator();
                SelectionKey key=null;
                while(it.hasNext()){
                    key=it.next();
                    it.remove();
                    try{
                      handleInput(key);
                    }catch (Exception e){
                        if(key !=null){
                            key.cancel();
                            if(key.channel() !=null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (Throwable e){
                e.printStackTrace();
            }
        }

        if(selector !=null){
            try{
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            //处理新接入的消息
            /**
             * 处理新接入的客户端请求消息，根据SelectionKey的操作位进行判断即可获知网络事件的类型，通过ServerSocketChannel的
             * accept接收客户端的连接请求并创建SocketChannel实例，完成上述操作后，相当于完成了TCP的三次握手，TCP物理链路正式建立
             * 注意：我们需要将新创建的SocketChannel设置为异步非阻塞，同时也可以对其TCP参数进行设置，例如TCP接收和发送缓冲区的大小等。
             */
            if(key.isAcceptable()){
                //accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //sc.socket().setReuseAddress(true);
                //add the new connection to the selector
                sc.register(selector,SelectionKey.OP_READ);
            }
            /**
             * 用于读取客户的请求消息。首先创建一个ByteBuffer，由于我们事先无法得知客户端发送的码流大小，作为例程，我们开辟一个1mb的缓冲区。
             * 然后调用SocketChannel的read方法读取请求码流。
             * 注意由于我们将SocketChannel设置位异步非阻塞模式，因此它的read是非阻塞的。使用返回值进行判断，看到读取的字节数，返回值有以下三种可能的结果
             *  返回值大于0:读到了字节，对自己进行编解码；
             *  返回值等于0:没有读取到字节，属于正常场景，忽悠
             *  返回值为-1:链路已经关闭，需要关闭SocketChannel，释放资源
             */
            if(key.isReadable()){
                //read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer  readBuffer= ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if(readBytes>0){
                    /**
                     * 当读取到码流以后，进行解码。首先readBuffer进行flip操作，它的作用是将缓冲区当前的limit设置为position，position设置为0，用于后续对缓冲区的读取操作，
                     * 然后根据缓冲区可读的字节个数创建字节数组，调用ByteBuffer的get操作将缓冲区可读的字节数组复制到新创建的字节数组中，最后调用字符串的构造函数创建请求消息并打印
                     * 如果请求指令是"QUERY TIME ORDER",则把服务器的当前时间编码后返回给客户端
                     */
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body=new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("The time server receive order:"+body);
                    String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    dowrite(sc,currentTime);
                }else if(readBytes<0){
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                }else{
                    System.out.println("无操作");
                }
            }
        }
    }

    /**
     * 首先将字符串编码成字节数组，根据字节数组的容量创建ByteBuffer，调用ByteBuffer的put操作将字节数组复制到缓冲区中，然后对缓冲区进行flip操作
     * 最后调用SocketChannel的write方法将缓冲区中的字节数组发送出去，需要指出的是，由于SocketChannel是异步非阻塞的，它并不保证一次能够把需要发送的紫萼数组发送完，
     * 此时会出现"写半包"问题。我们需要注册写操作，不断轮询Selector将没有发送完的ByteBuffer发送完毕，然后可以通过ByteBuffer的hasRemain()方法炮弹段消息释放发送完成。
     */
    private void dowrite(SocketChannel channel,String response) throws IOException {
        if(response !=null && response.trim().length()>0){
            byte[] bytes = response.getBytes();
            ByteBuffer allocate = ByteBuffer.allocate(bytes.length);
            allocate.put(bytes);
            allocate.flip();
            channel.write(allocate);
        }
    }

}
