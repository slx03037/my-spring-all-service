package demo.executor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author shenlx
 * @description
 * @date 2024/2/4 15:22
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MyRunable implements Runnable{
    public String command;

    @Override
    public void run() {
        Logger logger=Logger.getLogger("MyRunable");
        logger.warning(Thread.currentThread().getName()+command+" Start. Time ="+new Date());
        processCommand();
        logger.warning(Thread.currentThread().getName()+command+" End. Time ="+new Date());
    }

    private void processCommand(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
