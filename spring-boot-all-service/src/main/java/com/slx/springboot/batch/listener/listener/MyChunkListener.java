package com.slx.springboot.batch.listener.listener;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-17 10:36
 **/
@Component
public class MyChunkListener  implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext context) {
        System.out.println("before chunk: " + context.getStepContext().getStepName());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        System.out.println("after chunk: " + context.getStepContext().getStepName());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        System.out.println("before chunk error: " + context.getStepContext().getStepName());
    }
}
