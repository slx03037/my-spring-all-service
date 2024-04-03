package demo.sendandreceivedata.thread;

import java.io.InputStream;
import java.net.Socket;
import java.util.function.IntPredicate;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 11:57
 */
public class EchoProtocol implements Runnable{
    private static final int BUFSIZE=32;

    private final Socket socket;

    private final Logger logger;

    //成员变量和构造函数  每个EchoProtocol实例都包含了一个响应连接得套接字和logger实例得引用
    public EchoProtocol(Socket socket,Logger logger){
        this.socket=socket;
        this.logger=logger;
    }

    /**
     *实现回显协议
     */
    public static void handleEchoClient(Socket socket ,Logger logger){
        try{
            InputStream inputStream = socket.getInputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            handleEchoClient(socket,logger);
    }
}
