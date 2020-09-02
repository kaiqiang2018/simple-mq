package com.kaiqiang.simple.mq.api;

import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/07/29
 */
public interface MessageService {

    /**
     * 发送一条消息
     */
    void sendMessage(List<CommonMessage> message) throws Exception;

    /**
     * 拉取要处理的消息
     */
    List<Message> pullMessage(String prefix, String group);
}
