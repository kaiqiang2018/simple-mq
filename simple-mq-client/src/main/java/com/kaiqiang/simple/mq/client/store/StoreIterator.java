package com.kaiqiang.simple.mq.client.store;


import com.kaiqiang.simple.mq.api.Message;

import java.util.Collections;
import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/08/02
 */
public class StoreIterator {

    private static final int batchSize = 50;

    private long startPos;

    private long currentPos;

    public StoreIterator(long startPos) {
        this.startPos = startPos;
        this.currentPos = startPos;
    }

    /**
     * @return maybe empty
     */
    public List<Message> next() {
        // TODO: 20-8-2
        return Collections.emptyList();
    }

    /**
     * 重置当前迭代位置
     *
     * @param currentPos
     */
    public void setCurrentPos(long currentPos) {
        this.currentPos = currentPos;
    }

    public long getStartPos() {
        return startPos;
    }

    public long getCurrentPos() {
        return currentPos;
    }
}
