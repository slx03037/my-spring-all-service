package com.slx.springboot.batch.itemreader.reader;

import org.springframework.batch.item.ItemReader;

import java.util.Iterator;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-01-16 19:48
 **/

public class MySimpleIteamReader implements ItemReader<String> {
    private Iterator<String> iterator;

    public MySimpleIteamReader(List<String> data) {
        this.iterator = data.iterator();
    }

    @Override
    public String read() {
        // 数据一个接着一个读取
        return iterator.hasNext() ? iterator.next() : null;
    }
}
