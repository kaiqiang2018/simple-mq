package com.kaiqiang.simple.mq.server.store.mysql;

import com.kaiqiang.simple.mq.api.Message;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * @Author kaiqiang
 * @Date 2020/09/02
 */
@Alias("MessageStoreEntity")
public class MessageStoreEntity {

    private long id;
    private String messageId;
    private String attr;
    private LocalDateTime createTime;

    public MessageStoreEntity() {
    }

    public static MessageStoreEntity fromMessage(Message message) {
        MessageStoreEntity entity = new MessageStoreEntity();
        entity.setMessageId(message.getMessageId());
        entity.setCreateTime(message.getCreateTime());
        entity.setAttr(message.serializationAttr());
        return entity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
