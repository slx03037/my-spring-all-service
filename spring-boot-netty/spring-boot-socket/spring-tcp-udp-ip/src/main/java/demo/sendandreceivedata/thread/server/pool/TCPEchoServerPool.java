package demo.sendandreceivedata.thread.server.pool;

import demo.sendandreceivedata.thread.EchoProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 13:24
 */
public class TCPEchoServerPool {

    private final static int threadPoolSize=10;

    public static void  main(String[]args)throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8081);

        final Logger logger=Logger.getLogger("practical");

        for(int i=0;i<threadPoolSize;i++){
            Thread thread = new Thread(){
                @Override
                public void run(){
                    while(true){
                       try{
                           Socket accept = serverSocket.accept();
                           EchoProtocol.handleEchoClient(accept,logger);
                       }catch (IOException e){
                           logger.log(Level.WARNING,"clenit accept failed",e);
                       }
                    }
                }
            };

            thread.start();
            logger.info("creat and start Thread="+thread.getName());

        }
    }
}
