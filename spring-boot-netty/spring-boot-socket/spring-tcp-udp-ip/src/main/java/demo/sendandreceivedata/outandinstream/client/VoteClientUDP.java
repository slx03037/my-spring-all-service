package demo.sendandreceivedata.outandinstream.client;

import demo.sendandreceivedata.outandinstream.VoteMsg;
import demo.sendandreceivedata.outandinstream.VoteMsgTextCoder;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 10:48
 */
public class VoteClientUDP {
    public static void main(String[]args) throws IOException {

        InetAddress allByName = InetAddress.getByName("127.0.0.1");

        DatagramSocket socket = new DatagramSocket();

        socket.connect(allByName,8989);

        VoteMsg voteMsg = new VoteMsg(false, false, 999, 0);

        VoteMsgTextCoder coder = new VoteMsgTextCoder();

        //send request
        byte[] towire = coder.towire(voteMsg);

        System.out.println("Sending Text-Encoded Request("+towire.length+"bytes):");

        DatagramPacket packet = new DatagramPacket(towire,towire.length);

        socket.send(packet);

        //Receive respone
         packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH], VoteMsgTextCoder.MAX_WIRE_LENGTH);

        socket.receive(packet);

        towire = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());

        System.out.println("Receive Text-Encoded Response("+towire.length+"bytes):");

         voteMsg = coder.fromWire(towire);

         System.out.println(voteMsg);

    }
}
