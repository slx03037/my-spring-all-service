package com.slx.springboot.async.service;

import java.util.concurrent.Future;

public interface AsyncService {
    /**
     * 异步调用
     * @return
     */
    public Future<String> asyncMethod();

    /**
     * 同步调用
     */
    public void syncMethod();
}
