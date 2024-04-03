package demo.udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author shenlx
 * @description
 * @date 2024/1/17 16:55
 */
public class UDPEchoServer {
    private static final int echomax=255;

    private static final int port=8089;

    public static void main(String[]args)throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(new byte[echomax],echomax);

        while(true){
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Handling clinet at:"+packet.getAddress().getHostAddress()+"on port"+packet.getPort()+" message："+message);

            socket.send(packet);

            packet.setLength(echomax);

        }
    }
}
