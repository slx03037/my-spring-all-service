package demo.multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 14:32
 */
public class TCPEchoServerThread {
    public static void main(String[]args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(8190);

        Logger logger=Logger.getLogger("practical");

        while(true){
            Socket clntSock=serverSocket.accept();

            Thread thread=new Thread(new EchoProtocol(clntSock,logger));

            thread.start();

            logger.info("Created and started Thread"+thread.getName());
        }
    }
}
