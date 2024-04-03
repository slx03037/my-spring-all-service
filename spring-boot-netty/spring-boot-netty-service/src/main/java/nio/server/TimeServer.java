package nio.server;

import nio.server.MultiplexerTimeServer;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 15:08
 */
public class TimeServer {
    public static void main(String[]args){
        int port=8080;
        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);
        new Thread(multiplexerTimeServer,"NIO-MultiplexerTimeServer-001").start();
    }
}
