package com.kaiqiang.simple.mq.client.store;

import com.kaiqiang.simple.mq.api.ProductMessage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientStore {

    /**
     * 持久化一条消息
     */
    void storeMessage(ProductMessage message) throws SQLException;

    /**
     * 删除一条消息
     */
    void deleteMessage(long messageId);


    /**
     * 更新一条消息状态
     */
    void updateMessageStatus(long messageId, int newStatus);

    /**
     * 更新重试次数 和 下一次重试时间
     *
     * @param sendCount 发送的次数
     * @param nextRetryTime 下一次重试发送时间
     */
    void updateSendCountAndRetryTime(long messageId, int sendCount, LocalDateTime nextRetryTime) throws SQLException;

    /**
     * 查找索引位置 > pos 且 status = CAN_SEND 且 nextSendTime > startSendTime 的 limit 个消息
     */
    List<ProductMessage> select(long pos, LocalDateTime startSendTime, int limit);
}
