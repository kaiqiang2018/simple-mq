package com.kaiqiang.simple.mq.server.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class LocalCache<T, C> {

    private final Map<T, C> cache = new HashMap<>();

    private final Function<T, C> transfer;

    /**
     * @param transfer not null
     */
    public LocalCache(Function<T, C> transfer) {
        Objects.requireNonNull(transfer, "Parameter 'transfer' should not be null.");
        this.transfer = transfer;
    }

    /**
     * 参考单例模式
     *
     * @param t not null
     */
    public C getValue(T t) {
        C cacheValue = cache.get(t);
        if(cacheValue == null) {
            synchronized (this) {
                cacheValue = cache.get(t);
                if(cacheValue == null) {
                    cacheValue = transfer.apply(t);
                    cache.put(t, cacheValue);
                }
            }
        }
        return cacheValue;
    }
}
