package com.kaiqiang.simple.mq.server;

import com.kaiqiang.simple.mq.api.Message;
import com.kaiqiang.simple.mq.api.MessageService;
import com.kaiqiang.simple.mq.api.exception.MessagePrefixNotAllowed;
import com.kaiqiang.simple.mq.server.config.SubjectAndConsume;
import com.kaiqiang.simple.mq.server.config.SubjectAndConsume.MessageStoreConfig;
import com.kaiqiang.simple.mq.server.store.MysqlServerStore;
import com.kaiqiang.simple.mq.server.store.ServerStore;

import java.util.*;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class MessageServiceImpl implements MessageService {

    private final ServerStore store = new MysqlServerStore();
    private final List<SubjectAndConsume> commonConfigs;
    private final Map<String, SubjectAndConsume> subjectConfigMap;

    public MessageServiceImpl() {
        // todo mock
        List<MessageStoreConfig> storeConfigs = Arrays.asList(
            new MessageStoreConfig("message_store_0001", 0),
            new MessageStoreConfig("message_store_0002", 0),
            new MessageStoreConfig("message_store_0003", 0)
        );

        commonConfigs = Collections.singletonList(
                new SubjectAndConsume("app.order.finished", storeConfigs)
        );

        subjectConfigMap = new HashMap<>();
        commonConfigs.forEach(v -> v.getStoreConfigs().forEach(t -> subjectConfigMap.put(v.getSubject(), v)));
    }

    @Override
    public void sendMessage(Message message) throws Exception {
        String subject = message.getSubject();
        SubjectAndConsume config = subjectConfigMap.get(subject);
        if(config == null) {
            throw new MessagePrefixNotAllowed("未获取到subject = [" + subject + "]的配置");
        }

        // 校验

        // 持久化
        store.storeMessage(message, config.getStoreTableName());
    }

    @Override
    public List<Message> pullMessage(String prefix, String group) {
        // TODO: 2020/8/8
        return null;
    }
}
