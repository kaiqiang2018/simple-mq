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

    // TODO: 2020/8/8  优化synchronized
    public synchronized C getValue(T t) {
        if(t == null) {
            throw new IllegalArgumentException("Parameter 't' should not be null.");
        }
        C cacheValue = cache.get(t);
        if(cacheValue != null) {
            return cacheValue;
        }

        C newValue = transfer.apply(t);
        cache.put(t, newValue);
        return newValue;
    }
}
