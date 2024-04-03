package demo.sendandreceivedata.outandinstream.server;

import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgTextCoder;
import demo.sendandreceivedata.outandinstream.service.VoteService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 10:59
 */
public class VoteServerUDP {
    public static void main(String[]args) throws IOException {

        //InetAddress byName = InetAddress.getByName("127.0.0.1");

        DatagramSocket socket = new DatagramSocket(8989);

        //socket.connect(byName,8989);

        byte[] bytes = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];

        VoteMsgTextCoder coder = new VoteMsgTextCoder();

        VoteService voteService = new VoteService();

        while(true){
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

            socket.receive(packet);

            byte[] encodeMsg = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());

            System.out.println("Handling request from"+packet.getSocketAddress()+"("+encodeMsg.length+"bytes)");

            try{
                VoteMsg msg = coder.fromWire(encodeMsg);
                 msg = voteService.HandlerRequest(msg);
                packet.setData(coder.towire(msg));

                System.out.println("Sending response("+packet.getLength()+"bytes)");

                System.out.println(msg);

                socket.send(packet);

            }catch (Exception e){
                e.printStackTrace();
                System.err.println("Parse error in message: "+e.getMessage());
            }

        }

    }
}
