package demo.sendandreceivedata.outandinstream;

import java.io.*;

/**
 * @author shenlx
 * @description
 * @date 2024/1/19 9:27
 */
public class VoteMsgBinCoder implements VoteMsgCoder{
    public static final int MIN_WIRE_LENGTH=4;

    public static final int MAX_WIRE_LENGTH=16;

    public static final int MAGIC=0X5400;

    public static final int MAGIC_MASK=0xfc00;

    public static final int MAGIC_SHIFT=8;

    public static final int RESPONSE_FLAG=0X0200;

    public static final int INQUIRE_FLAG=0X0100;

    @Override
    public byte[] towire(VoteMsg msg) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArrayOutputStream);

        short magicAndFlags=MAGIC;

        if(msg.isInquiry()){
            magicAndFlags = INQUIRE_FLAG;
        }

        if(msg.isResponse()){
            magicAndFlags=RESPONSE_FLAG;
        }

        out.writeShort(magicAndFlags);

        out.writeShort((short)msg.getCandidateID());

        if(msg.isResponse()){
            out.writeLong(msg.getVoteCount());
        }
        out.flush();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return byteArray;
    }

    @Override
    public VoteMsg fromWire(byte[] input) throws IOException {

        if(input.length<MIN_WIRE_LENGTH){
            throw new IOException("RUNT MESSAGE");
        }

        ByteArrayInputStream bs = new ByteArrayInputStream(input);

        DataInputStream in = new DataInputStream(bs);

        int b = in.readByte();

        if((b & MAGIC_MASK) !=MAGIC){
            throw new IOException("Bad Magic#:"+((b & MAGIC_MASK)>>MAGIC_SHIFT));
        }

        boolean resp=((b & RESPONSE_FLAG) !=0);

        boolean inq=((b & INQUIRE_FLAG) !=0);

        int candidateID = in.readShort();

        if(candidateID<0 || candidateID >100){
            throw new IOException("Bad candidate ID:"+candidateID);
        }

        long count=0;

        if(resp){
            count=in.readLong();

            if(count<0){
                throw  new IOException("Bad vote count:"+count);
            }
        }

        return new VoteMsg(resp,inq,candidateID,count);
    }
}
