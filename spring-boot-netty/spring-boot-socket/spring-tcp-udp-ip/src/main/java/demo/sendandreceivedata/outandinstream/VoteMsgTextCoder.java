package demo.sendandreceivedata.outandinstream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author shenlx
 * @description VoteMsgTextCoder类提供了一种基于文本的VoteMsg编码方法
 * @date 2024/1/18 15:05
 */
public class VoteMsgTextCoder implements VoteMsgCoder{

    public static final String MAGIC="Voting";

    public static final String VOTESTR="v";

    public static final String INOSTR="i";

    public static final String RESPONSESTR="R";

    public static final String CHARSETNAME="US-ASCII";

    public static final String DELIMSTR="";

    public static final int MAX_WIRE_LENGTH=2000;

    @Override
    public byte[] towire(VoteMsg msg) throws IOException {
        String msgStr=MAGIC+DELIMSTR+(msg.isInquiry()?INOSTR:VOTESTR)
                + DELIMSTR+(msg.isResponse()?RESPONSESTR+DELIMSTR:"")
                + msg.getCandidateID() +DELIMSTR
                + msg.getVoteCount();

        byte[] data =msgStr.getBytes(CHARSETNAME);
        return data;
    }

    @Override
    public VoteMsg fromWire(byte[] message) throws IOException {
        ByteArrayInputStream msgStr=new ByteArrayInputStream(message);
        Scanner s=new Scanner(new InputStreamReader(msgStr,CHARSETNAME));

        boolean isInquiry;
        boolean isResponse;
        int candidateID;
        long voteCount;
        String token;
        try{
//            token=s.next();
//            if(!token.equals(MAGIC)){
//                throw new IOException("Bad magic String:"+token);
//            }
            token=s.next();
            if(token.equals(VOTESTR)){
                isInquiry=false;
            }else if (!token.equals(INOSTR)){
                throw new IOException("Bad vote/inq inDicator: "+token);
            }else {
                isInquiry=true;
            }

            token =s.next();
            if(token.equals(RESPONSESTR)){
                isResponse=true;
                token=s.next();
            }else {
                isResponse=false;
            }
            candidateID=Integer.parseInt(token);
            if(isResponse){
                token =s.next();
                voteCount=Long.parseLong(token);
            }else {
                voteCount=0;
            }
        }catch (IOException e){
            e.printStackTrace();
            throw new IOException("Parse error....");
        }
        return new VoteMsg(isResponse,isInquiry,candidateID,voteCount);
    }
}
