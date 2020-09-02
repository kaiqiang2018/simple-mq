package com.kaiqiang.simple.mq.server.store.mysql;

import com.kaiqiang.simple.mq.api.Message;
import com.kaiqiang.simple.mq.server.store.ServerStore;

import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/09/02
 */
public class MysqlServerStore implements ServerStore {

    @Override
    public void storeMessage(List<Message> message, String tableName) {

    }
}
