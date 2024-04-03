package nio.client;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 16:42
 */
public class TimeClient {
    public static void main(String[]args){
        int port=8080;
        new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();
    }
}
