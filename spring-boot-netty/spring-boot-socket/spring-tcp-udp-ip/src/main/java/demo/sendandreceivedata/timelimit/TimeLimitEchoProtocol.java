package demo.sendandreceivedata.timelimit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 14:15
 */
public class TimeLimitEchoProtocol implements Runnable{

    private static final int BUFSIZE=32;

    private static final String TIMELIMIT="1000";

    private static final String TIMELIMITPROP="Timelimit";

    private static int timelimit;

    private final Socket socket;

    private final Logger logger;

    public TimeLimitEchoProtocol(Socket socket,Logger logger){
        this.socket=socket;
        this.logger=logger;
        Integer.parseInt(System.getProperty(TIMELIMITPROP,TIMELIMIT));
    }

    public static void handlerEchoClient(Socket socket,Logger logger){
        try{
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            int recvMsgSize;

            int totalBytesEchoed=0;

            byte[] bytes = new byte[BUFSIZE];

            long timeMillis = System.currentTimeMillis();

            int timeIntMillis=(int)timeMillis;

            socket.setSoTimeout(timeIntMillis);

            while((timeIntMillis>0) && ((recvMsgSize=in.read())!=-1)){
                out.write(bytes,0,recvMsgSize);

                totalBytesEchoed+=recvMsgSize;

                timeIntMillis=(int)(timeMillis-System.currentTimeMillis());

                socket.setSoTimeout(timeIntMillis);
            }
            logger.info("Client "+socket.getRemoteSocketAddress()
                    +",echoed"+totalBytesEchoed+"bytes."
            );


        }catch (IOException e){
            e.printStackTrace();
            logger.log(Level.WARNING,"Exception in echo protocol",e);
        }
    }

    @Override
    public void run() {
        handlerEchoClient(this.socket,this.logger);
    }
}
