package com.kaiqiang.simple.mq.server;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.kaiqiang.simple.mq.api.MessageService;

import java.io.IOException;

/**
 * @Author kaiqiang
 * @Date 2020/08/08
 */
public class BootStrap {

    public static final String appName = "simple-mq-server";

    public static void main(String[] args) throws InterruptedException, IOException {
        // dubbo 配置
        MessageService messageService = new MessageServiceImpl();
        ApplicationConfig application = new ApplicationConfig();
        application.setName(appName);

        // zookeeper
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("127.0.0.1:2181");
        registry.setProtocol("zookeeper");

        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setThreads(200);

        ServiceConfig<MessageService> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(MessageService.class);
        service.setRef(messageService);
        service.setVersion("1.0.0");

        service.export();

        System.out.println("输入任意键退出");
        System.in.read();
    }
}
