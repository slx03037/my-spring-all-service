package demo.multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 15:04
 */
public class TCPEchoServerPool {
    public static void main(String[]args) throws IOException {
        int threadPoolSize=10;
        final ServerSocket socket=new ServerSocket(8191);
        final Logger logger=Logger.getLogger("practical");

        for(int i=0;i<threadPoolSize;i++){
            Thread thread=new Thread(){
              @Override
              public void run(){
                  while(true){
                      try{
                          Socket clntSock=socket.accept();
                          EchoProtocol.handlerEchoClient(clntSock,logger);
                      } catch (IOException e) {
                          logger.log(Level.WARNING,"Clent accpet failed",e);
                          throw new RuntimeException(e);
                      }
                  }
              }
            };
            thread.start();
            logger.info("Created and started Thread="+thread.getName());
        }

    }
}
