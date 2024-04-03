package demo.servier;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Date;

/**
 * @author shenlx
 * @description
 * @date 2024/1/11 17:02
 */
public class TimeServerHandler implements Runnable{
    private Socket socket;

    public TimeServerHandler(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        try{
            in = new BufferedReader(new InputStreamReader(
                    this.socket.getInputStream()
            ));
             out = new PrintWriter(this.socket.getOutputStream(), true
            );
             String currtTime=null;
             String body=null;
             while (true){
                 body=in.readLine();
                 if(body==null){
                     break;
                 }
                 System.out.println("The time server receive order:"+body);
                 currtTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(
                         System.currentTimeMillis()
                 ).toString():"BAD ORDER";
                 out.println(currtTime);
             }

        } catch (IOException e) {
           if(in !=null){
               try{
                   in.close();
               }catch (IOException es){
                   es.printStackTrace();
               }
           }
           if(out !=null){
               out.close();
               out=null;
           }
           if(this.socket !=null){
               try{
                   this.socket.close();
               }catch (IOException es){
                   es.printStackTrace();
               }
               this.socket=null;
           }
        }

    }
}
