package com.kaiqiang.simple.mq.server.config;

import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class SubjectAndConsume {

    private String subject;
    private List<MessageStoreConfig> storeConfigs;
    private int index;

    public SubjectAndConsume(String subject, List<MessageStoreConfig> storeConfigs) {
        this.subject = subject;
        this.storeConfigs = storeConfigs;
        this.index = 0;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<MessageStoreConfig> getStoreConfigs() {
        return storeConfigs;
    }

    /**
     * 轮询获取存储的表名
     */
    public String getStoreTableName() {
        String tableName = storeConfigs.get(index).messageStoreTableName;
        this.index = (this.index + 1) % storeConfigs.size();
        return tableName;
    }

    public void setStoreConfigs(List<MessageStoreConfig> storeConfigs) {
        this.storeConfigs = storeConfigs;
    }

    public static class MessageStoreConfig {
        private String messageStoreTableName;
        private long consumeQueueDeliveryId;

        public MessageStoreConfig(String messageStoreTableName, long consumeQueueDeliveryId) {
            this.messageStoreTableName = messageStoreTableName;
            this.consumeQueueDeliveryId = consumeQueueDeliveryId;
        }

        public String getMessageStoreTableName() {
            return messageStoreTableName;
        }

        public void setMessageStoreTableName(String messageStoreTableName) {
            this.messageStoreTableName = messageStoreTableName;
        }

        public long getConsumeQueueDeliveryId() {
            return consumeQueueDeliveryId;
        }

        public void setConsumeQueueDeliveryId(long consumeQueueDeliveryId) {
            this.consumeQueueDeliveryId = consumeQueueDeliveryId;
        }
    }
}
