package demo.sendandreceivedata.thread.server;

import demo.sendandreceivedata.thread.EchoProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 13:18
 */
public class TCPEchoServerThread {
    public static void main(String[]args)throws IOException {
        ServerSocket socket=new ServerSocket(8989);

        Logger logger=Logger.getLogger("practical");

        while(true){
            Socket accept = socket.accept();

            Thread thread = new Thread(new EchoProtocol(accept, logger));

            thread.start();

            logger.info("created and started Thread"+thread.getName());

        }
    }
}
