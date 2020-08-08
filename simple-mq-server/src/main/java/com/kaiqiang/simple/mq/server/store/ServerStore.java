package com.kaiqiang.simple.mq.server.store;

import com.kaiqiang.simple.mq.api.Message;

import java.sql.SQLException;

public interface ServerStore {

    /**
     * 持久化一条消息
     */
    void storeMessage(Message message, String tableName) throws SQLException;
}
