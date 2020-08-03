package com.kaiqiang.simple.mq.client;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author kaiqiang
 * @Date 2020/07/29
 */
public class SimpleIdGenerator implements IdGenerator {


    private static final AtomicLong id = new AtomicLong(0);

    // TODO: 20-8-2  临时写
    @Override
    public long getNextId() {
        return System.currentTimeMillis() * 100 + id.addAndGet(1);
    }
}
