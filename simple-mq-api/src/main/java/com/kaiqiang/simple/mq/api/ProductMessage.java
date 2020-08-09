package com.kaiqiang.simple.mq.api;

import java.time.LocalDateTime;

/**
 * @Author kaiqiang
 * @Date 2020/08/02
 */
public class ProductMessage extends CommonMessage {

    /**
     * 当前已发送次数
     */
    private int sendCount;

    /**
     * 下一次发送时间
     */
    private LocalDateTime nextSendTime;

    /**
     * CAN_SEND 可以发送至server
     * NOT_ALLOWED 生产者未在server注册该前缀
     */
    public static final int CAN_SEND = 0, NOT_ALLOWED = 1;
    /**
     * 当前状态
     */
    private int state;

    /**
     * 是否持久化
     */
    private boolean stored;

    public ProductMessage(long messageId, String subject, LocalDateTime createTime) {
        super(messageId, subject, createTime);
        this.setSendCount(0);
        this.setNextSendTime(LocalDateTime.now());
        this.setState(CAN_SEND);
    }

    public int getSendCount() {
        return sendCount;
    }

    public LocalDateTime getNextSendTime() {
        return nextSendTime;
    }

    public int getState() {
        return state;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public void setNextSendTime(LocalDateTime nextSendTime) {
        this.nextSendTime = nextSendTime;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isStored() {
        return stored;
    }

    public void setStored(boolean stored) {
        this.stored = stored;
    }
}
