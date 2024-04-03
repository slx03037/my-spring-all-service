package sync.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 13:19
 */
public class TimeClient {
    public static void main(String[]args){
        int port=8080;

        Socket socket=null;

        BufferedReader in =null;

        PrintWriter out=null;

        try{
            socket=new Socket("127.0.0.1",port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            out.println("QUERY TIME ORDER");
            System.out.println("send order 2 server succeed");
            String resp=in.readLine();
            System.out.println("Now is:"+resp);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out !=null){
                out.close();
                out=null;
            }
            if (in !=null){
                try{
                    in.close();
                }catch (IOException e1){
                    e1.printStackTrace();
                }
                in=null;
            }

            if(socket !=null){
                try{
                    socket.close();
                }catch (IOException e2){
                    e2.printStackTrace();
                }
                socket=null;
            }
        }
    }
}
