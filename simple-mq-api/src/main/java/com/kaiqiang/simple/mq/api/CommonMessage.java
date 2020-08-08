package com.kaiqiang.simple.mq.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiqiang.simple.mq.api.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author kaiqiang
 * @Date 2020/07/29
 */
public class CommonMessage implements Message {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private long messageId;

    private String subject;

    private LocalDateTime createTime;

    private Map<String, String> attr;

    public CommonMessage(long messageId, String subject, LocalDateTime createTime) {
        this.messageId = messageId;
        this.subject = subject;
        this.createTime = createTime;
        this.attr = new HashMap<>();
    }

    @Override
    public long getMessageId() {
        return messageId;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public String getProperty(String key) {
        return attr.get(key);
    }

    @Override
    public void setProperty(String key, String value) {
        if(key == null || key.length() == 0) {
            throw new IllegalArgumentException("Parameter 'key' should not be empty.");
        }
        if(value == null) {
            throw new IllegalArgumentException("Parameter 'value' should not be null.");
        }

        if(attr.containsKey(key)) {
            throw new RuntimeException("Key " + key + " already exists.");
        }

        attr.put(key, value);
    }

    @Override
    public Map<String, String> getAllProperty() {
        return attr;
    }

    @Override
    public String storeAttr() {
        try {
            return objectMapper.writeValueAsString(attr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化attr字段失败", e);
        }
    }
}
