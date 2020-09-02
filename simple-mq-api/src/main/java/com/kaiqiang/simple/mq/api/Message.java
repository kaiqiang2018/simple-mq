package com.kaiqiang.simple.mq.api;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @Author kaiqiang
 * @Date 2020/07/19
 */
public interface Message {

    String getMessageId();

    String getSubject();

    LocalDateTime getCreateTime();

    // -------------------------------------------
    // 设置、读取属性
    String getProperty(String key);

    void setProperty(String key, String value);

    Map<String, String> getAllProperty();

    String serializationAttr();
}
























