package demo.multithreading;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 14:06
 */
public class EchoProtocol implements Runnable{
    private static final int BUFSIZE=32;
    private final Socket clntSocket;
    private final Logger logger;

    public EchoProtocol(Socket clntSocket,Logger logger){
        this.clntSocket=clntSocket;
        this.logger=logger;
    }

    public static void handlerEchoClient(Socket clntSocket,Logger logger){
        try{
            InputStream in=clntSocket.getInputStream();

            OutputStream out=clntSocket.getOutputStream();

            int receiveSize;

            int totalBytesEchoed=0;

            byte[]echoBUffer=new byte[BUFSIZE];

            while((receiveSize=in.read(echoBUffer)) != -1){
                out.write(echoBUffer,0,receiveSize);
                totalBytesEchoed +=receiveSize;
            }

            logger.info("Client"+clntSocket.getRemoteSocketAddress()+",echoed"+totalBytesEchoed+"bytes.");

        }catch (IOException e){
            e.printStackTrace();
            logger.log(Level.WARNING,"Exception in echo protocol",e);
        }finally {
            try{
                clntSocket.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        handlerEchoClient(clntSocket,logger);
    }
}
