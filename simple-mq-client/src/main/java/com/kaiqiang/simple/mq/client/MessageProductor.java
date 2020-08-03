package com.kaiqiang.simple.mq.client;


import com.kaiqiang.simple.mq.api.MessageService;
import com.kaiqiang.simple.mq.api.exception.MessagePrefixNotAllowed;
import com.kaiqiang.simple.mq.client.productor.ProductMessage;
import com.kaiqiang.simple.mq.client.store.ClientStore;
import com.kaiqiang.simple.mq.client.store.MysqlClientStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kaiqiang
 * @Date 2020/07/29
 */
public class MessageProductor {

    private static final Logger log = LoggerFactory.getLogger(MessageProductor.class);

    private static final IdGenerator idGenerator = new SimpleIdGenerator();

    private MessageService messageService = null;

    private ClientStore clientStore = new MysqlClientStore();

    public ProductMessage generateMessage(String subject) {
        ProductMessage message = new ProductMessage(idGenerator.getNextId(), subject, LocalDateTime.now());
        message.setStored(false);
        return message;
    }

    /**
     *
     */
    public void sendMessage(ProductMessage message) {

        // 检查 null, 消息是否有参数, 消息字段长度, attr大小等等
        // 状态必须为CAN_SEND
        // 如果通过校验，则该消息一定可以持久化和发送至server

        try {
            messageService.sendMessage(message);
        } catch (Exception e) {
            //log.error("发送消息失败, message = {}", message, e);
            boolean notAllowed = e instanceof MessagePrefixNotAllowed;
            try {
                if(message.isStored()) {
                    if(notAllowed) {
                        clientStore.updateMessageStatus(message.getMessageId(), ProductMessage.NOT_ALLOWED);
                    } else {
                        int sendCount = message.getSendCount() + 1;
                        clientStore.updateSendCountAndRetryTime(message.getMessageId(), sendCount, getNextSendTime(sendCount));
                    }
                } else {
                    if(notAllowed) {
                        message.setState(ProductMessage.NOT_ALLOWED);
                    }
                    clientStore.storeMessage(message);
                }
            } catch (Exception ex) {
                // TODO: 20-8-2
                log.error("发送消息后持久化或更新状态失败", ex);
            }
        }
    }

    /**
     * 发送退让策略
     *
     * @param currentSendCount 当前已发送次数
     */
    private LocalDateTime getNextSendTime(int currentSendCount) {
        // TODO: 20-8-2
        return LocalDateTime.now().plusMinutes(5);
    }

    // TODO: 20-8-2 Test
    public static void main(String[] args) throws InterruptedException {
        MessageProductor productor = new MessageProductor();

        long start = System.currentTimeMillis();

        List<Thread> worker = new ArrayList<>();
        for (int c = 0; c < 30; c++) {
            worker.add(new Thread(() -> {
                for (int i = 0; i < 10000; i++) {
                    try {
                        ProductMessage message = productor.generateMessage("app.order.finished");
                        message.setProperty("userName", "kaiqiang" + i);
                        message.setProperty("salary", "71990000" + i);
                        productor.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }));
        }
        for (Thread w : worker) {
            w.start();
            w.join();
        }

        System.out.println("time " + (System.currentTimeMillis() - start));
    }
}
