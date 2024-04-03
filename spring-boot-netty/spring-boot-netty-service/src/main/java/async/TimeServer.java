package async;

import sync.server.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.channels.Channel;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 13:39
 */
public class TimeServer {
    /**
     * 伪异步I/O的主函数代码发送了编码，我们首先创建一个事件服务器处理类的线程池，当接收到新的客户端连接时，
     * 将请求socket封装成一个task，然后调用线程池的execute方法执行，从而避免了每个请求接入都创建一个新的线程
     * @param args
     * @throws IOException
     */
    public static void main(String[]args) throws IOException {
        int port=8080;
        ServerSocket server=null;
        //Buffer
        try{
            server = new ServerSocket(port);
            System.out.println("The time sever is start in port:"+port);
            Socket socket=null;
            //多线程和任务队列异步I/O
            TimeServerHandlerExecutePool timeServerHandlerExecutePool = new TimeServerHandlerExecutePool(50, 10000);
            while(true){
                socket=server.accept();
                timeServerHandlerExecutePool.execute(new TimeServerHandler(socket));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(server !=null){
                System.out.println("The time server close");
                server.close();
                server=null;
            }
        }

    }
}
