package async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shenlx
 * @description
 * @date 2024/2/20 13:43
 */
public class TimeServerHandlerExecutePool {
    private final ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){
        executorService = new ThreadPoolExecutor(
                Runtime.getRuntime()
                        .availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize)
        );
    }

    public void execute(Runnable task){
        executorService.execute(task);
    }
}
