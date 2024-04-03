package demo.sendandreceivedata.receive;

import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgTextCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 15:43
 */
public class VoteMulicastReceiver {

    public static final String addr="224.0.0.1";

    public static final int port=8989;

    public static void main(String[]args)throws IOException {

        InetAddress byName = InetAddress.getByName(addr);

        MulticastSocket socket = new MulticastSocket(port);

        socket.joinGroup(byName);

        VoteMsgTextCoder coder = new VoteMsgTextCoder();

        DatagramPacket packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],VoteMsgTextCoder.MAX_WIRE_LENGTH);

        socket.receive(packet);

        VoteMsg voteMsg = coder.fromWire(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));

        System.out.println("Received Text-Encoded Request ("+packet.getLength()+"bytes);");

        System.out.println(voteMsg);

        socket.close();

    }
}
