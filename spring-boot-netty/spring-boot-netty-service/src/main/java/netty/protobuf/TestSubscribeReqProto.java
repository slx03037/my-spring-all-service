package netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/2/27 9:30
 */
public class TestSubscribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq
                .newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("lll");
        builder.setProductName("Netty book");
        builder.setAddress("Nanjing");
        return  builder.build();
    }

    public static void main(String[]args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req=createSubscribeReq();
        System.out.println("Before encode:"+ req);
        SubscribeReqProto.SubscribeReq req2=decode(encode(req));
        System.out.println("After decode:"+ req);
        System.out.println("Assert equal:--->"+req2.equals(req));
    }

    //private static SubscribeRespProto.SubscribeResp
}
