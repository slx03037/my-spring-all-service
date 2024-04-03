package demo.servier; /**
 * @author shenlx
 * @description
 * @date 2024/1/11 16:49
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞式I/O
 */
public class TimeServer {
    public static void main(String[]args) throws IOException {
        int port=8080;
        if(args !=null && args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                System.out.println("---");
            }
        }
        ServerSocket server=null;
        try{
            server=new ServerSocket(port);
            System.out.println("The time server is start in port:"+port);
            Socket socket= null;
            while (true){
                socket=server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(server !=null){
                System.out.println("The time server close");
                server.close();
                server=null;
            }
        }
    }
}
