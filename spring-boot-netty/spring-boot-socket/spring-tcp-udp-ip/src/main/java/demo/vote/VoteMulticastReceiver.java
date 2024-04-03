package demo.vote;

import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgTextCoder;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 16:10
 */
public class VoteMulticastReceiver {
    public static void main(String[]args) throws IOException {
        InetAddress byName = InetAddress.getByName("8191");


        MulticastSocket socket=new MulticastSocket(8191);

        socket.joinGroup(byName);

        VoteMsgTextCoder coder = new VoteMsgTextCoder();

        DatagramPacket packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],VoteMsgTextCoder.MAX_WIRE_LENGTH);

        socket.receive(packet);

        VoteMsg voteMsg = coder.fromWire(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));

        System.out.println("received Text-Encoded Request("+packet.getLength()+"bytes);");

        System.out.println(voteMsg);

        socket.close();

    }
}
