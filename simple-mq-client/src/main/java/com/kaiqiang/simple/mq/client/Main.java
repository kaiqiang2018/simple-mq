package com.kaiqiang.simple.mq.client;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.kaiqiang.simple.mq.api.MessageService;
import com.kaiqiang.simple.mq.api.ProductMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("simple-mq-client");

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");

        ReferenceConfig<MessageService> reference = new ReferenceConfig<>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(MessageService.class);
        reference.setVersion("1.0.0");

        MessageService messageService = reference.get();

        MessageProductor productor = new MessageProductor(messageService);
        long start = System.currentTimeMillis();
        List<Thread> worker = new ArrayList<>();
        for (int c = 0; c < 200; c++) {
            worker.add(new Thread(() -> {
                for (int i = 0; i < 500; i++) {
                    try {
                        ProductMessage message = productor.generateMessage("app.order.finished");
                        message.setProperty("userName", "kaiqiang" + i);
                        message.setProperty("salary", "71990000" + i);
                        for (int keyNum = 0; keyNum < 20; keyNum++) {
                            message.setProperty("key" + keyNum, getRandomString(100));
                        }
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

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = ThreadLocalRandom.current().nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
