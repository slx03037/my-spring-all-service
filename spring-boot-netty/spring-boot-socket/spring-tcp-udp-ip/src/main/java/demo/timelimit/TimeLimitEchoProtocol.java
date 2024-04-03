package demo.timelimit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 15:50
 */
public class TimeLimitEchoProtocol implements Runnable{
    private static final int BUFSIZE=32;

    private static final String TIMELIMIT="10000";

    private static final String TIMELIMITPROP="timelimit";

    private static int timelimit;

    private final Socket clnSock;

    private final Logger logger;

    public TimeLimitEchoProtocol(Socket clnSock,Logger logger){
        this.clnSock=clnSock;
        this.logger=logger;
        timelimit=Integer.parseInt(System.getProperty(TIMELIMITPROP,TIMELIMIT));
    }

    public static void handlerEchoClient(Socket clnSock,Logger logger){
        try{
            InputStream inputStream = clnSock.getInputStream();
            OutputStream outputStream = clnSock.getOutputStream();

            int receiveMsgSize;

            int totalbytesEchoed=0;

            byte[] bytes = new byte[BUFSIZE];

            long endTime = System.currentTimeMillis()+timelimit;

            int timeBoundMills= timelimit;

            clnSock.setSoTimeout(timeBoundMills);
            /**
             * 设置过期时间 防止数据太大一直阻塞
             */
            while((timeBoundMills>0) && (receiveMsgSize = inputStream.read()) != -1){
                outputStream.write(bytes,0,receiveMsgSize);

                totalbytesEchoed +=receiveMsgSize;

                timeBoundMills=(int)(endTime-System.currentTimeMillis());

                clnSock.setSoTimeout(timeBoundMills);
            }
            logger.info("client"+clnSock.getRemoteSocketAddress()+",echoed"+totalbytesEchoed+"bytes");

        } catch (IOException e) {
            logger.log(Level.WARNING,"Exception in echo protocol",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        handlerEchoClient(this.clnSock,this.logger);
    }
}
