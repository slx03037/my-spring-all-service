package demo.multithreading;

import demo.executor.ThreadExecutorService;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 15:09
 */
public class TCPEchoServerExecutor {
    public static void main(String[]args) throws IOException {
        ServerSocket socket=new ServerSocket(9091);

        Logger logger=Logger.getLogger("Practical");
        //手动创建线程会更好
        //ExecutorService executorService = Executors.newCachedThreadPool();

        ThreadExecutorService threadExecutorService=new ThreadExecutorService();

        while(true){
            Socket clntSock=socket.accept();
           // executorService.execute(new EchoProtocol(clntSock,logger));
            threadExecutorService.threadHandler();
            threadExecutorService.threadEchoProtocal(new EchoProtocol(clntSock,logger));

        }



    }
}
