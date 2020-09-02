package com.kaiqiang.simple.mq.server.store;

import com.kaiqiang.simple.mq.api.Message;

import java.util.List;

public interface ServerStore {

    /**
     * 持久化消息
     */
    void storeMessage(List<Message> message, String tableName);
}
