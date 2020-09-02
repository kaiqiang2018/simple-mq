package com.kaiqiang.simple.mq.api;

/**
 * @Author kaiqiang
 * @Date 2020/09/02
 */
public class MessageException extends RuntimeException {

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
