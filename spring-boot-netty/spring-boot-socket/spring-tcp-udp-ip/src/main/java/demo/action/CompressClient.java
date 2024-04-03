package demo.action;

import java.io.*;
import java.net.Socket;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 17:14
 */
public class CompressClient {
    public static final int BUFSIZE=256;

    public static void main(String[]args) throws IOException {
        String fileName="D://log.txt";
        FileInputStream in = new FileInputStream(fileName);

        FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".gz");

        Socket socket=new Socket("localhost",8191);

        sendBytes(socket,in);

        InputStream inputStream = socket.getInputStream();

        int bytesRead;

        byte[] buffer=new byte[BUFSIZE];

        while((bytesRead = inputStream.read()) != -1){
            fileOutputStream.write(buffer,0,bytesRead);

            System.out.println("R");
        }

        socket.close();
        in.close();
        fileOutputStream.close();

    }

    private static void sendBytes(Socket socket, InputStream in) throws IOException {
        OutputStream out = socket.getOutputStream();
        int bytesRead;
        byte[] bytes = new byte[BUFSIZE];

        while((bytesRead = in.read()) != -1){
            out.write(bytes,0,bytesRead);
            System.out.println("W");
        }
        socket.shutdownInput();
    }
}
