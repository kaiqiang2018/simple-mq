package com.kaiqiang.simple.mq.server;

import com.kaiqiang.simple.mq.api.CommonMessage;
import com.kaiqiang.simple.mq.api.Message;
import com.kaiqiang.simple.mq.api.MessageService;

import java.time.LocalDateTime;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class Test {

    public static void main(String[] args) throws Exception {
        MessageService messageService = new MessageServiceImpl();
        for (int i = 0; i < 100; i++) {
            Message message = new CommonMessage(System.currentTimeMillis() * 1000 + i, "app.order.finished", LocalDateTime.now());
            message.setProperty("orderNo", i + "-tieyou-" + System.currentTimeMillis());
            message.setProperty("userName", "apple");
            messageService.sendMessage(message);
        }
    }
}
