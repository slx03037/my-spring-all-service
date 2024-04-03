package demo.sendandreceivedata.outandinstream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author shenlx
 * @description
 * @date 2024/1/18 9:49
 */
public interface Framer {

    /**添加成帧信息并指定消息输出到指定流*/
    void frameMsg(byte[]message, OutputStream out)throws IOException;

    /**扫描指定的流，从中抽取出下条信息*/
    byte[] nextMsg()throws IOException;
}
