package demo.sendandreceivedata.outandinstream;

import java.io.IOException;

/**
 * @author shenlx
 * @description
 * @date 2024/1/18 15:00
 */
public interface VoteMsgCoder {
    /**towire()方法用于根据 一个特定的协议，将投票消息转换成一个字节序列*/
    byte[] towire(VoteMsg msg)throws IOException;

    /**fromWire()方法则根据相同的协议，对给定的字节序列进行解析，并根据信息的内容构造出消息类的实例*/
    VoteMsg fromWire(byte[]input)throws IOException;
}
