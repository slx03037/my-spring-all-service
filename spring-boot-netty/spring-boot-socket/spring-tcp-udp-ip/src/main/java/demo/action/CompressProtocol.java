package demo.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 17:25
 */
public class CompressProtocol implements Runnable{
    private static final int BUFSIZE=1024;

    private final Socket clntSock;

    private final Logger logger;

    public CompressProtocol(Socket clntSock,Logger logger){
        this.clntSock=clntSock;
        this.logger=logger;
    }

    public static void handlerCompressClient(Socket clntSock,Logger logger){
        try{
            InputStream in=clntSock.getInputStream();

            GZIPOutputStream out = new GZIPOutputStream(clntSock.getOutputStream());

            byte[] bytes = new byte[BUFSIZE];

            int bytesRead;

            while((bytesRead = in.read()) != -1){
                out.write(bytes,0,bytesRead);
                out.flush();
            }
            logger.info("client"+clntSock.getRemoteSocketAddress()+"finished");

        }catch (Exception e){
            logger.log(Level.WARNING,"Exception in echo protocol",e);
        }

        try {
            clntSock.close();
        }catch (IOException e){
            logger.info("Exception="+e.getMessage());
        }
    }

    @Override
    public void run() {
        handlerCompressClient(this.clntSock,this.logger);
    }
}
