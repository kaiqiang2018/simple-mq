package com.kaiqiang.simple.mq.api;

import com.kaiqiang.simple.mq.api.exception.MessagePrefixNotAllowed;
import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/07/29
 */
public interface MessageService {

    /**
     * 发送一条消息
     *
     * @throws MessagePrefixNotAllowed 消息前缀不被允许
     */
    void sendMessage(Message message) throws Exception;

    /**
     * 拉取要处理的消息
     */
    List<Message> pullMessage(String prefix, String group);
}
