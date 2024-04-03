package demo.multithreading;

import java.util.concurrent.TimeUnit;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 14:00
 */
public class ThreadExample implements Runnable{
    private final String greeting;

    public ThreadExample(String greeting){
        this.greeting=greeting;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("线程名称:"+Thread.currentThread().getName()+"  线程id:"+Thread.currentThread().getId()+":"+greeting);
            try{
                TimeUnit.MICROSECONDS.sleep((long)Math.random()*100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[]args){
        new Thread(new ThreadExample("hello")).start();
        new Thread(new ThreadExample("aloha")).start();
        new Thread(new ThreadExample("ciao")).start();
    }
}
