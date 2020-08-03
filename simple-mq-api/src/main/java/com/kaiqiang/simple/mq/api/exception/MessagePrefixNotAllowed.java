package com.kaiqiang.simple.mq.api.exception;

/**
 * 消息主题不被允许
 *
 * @Author kaiqiang
 * @Date 2020/08/02
 */
public class MessagePrefixNotAllowed extends RuntimeException {

    public MessagePrefixNotAllowed(String message) {
        super(message);
    }
}
