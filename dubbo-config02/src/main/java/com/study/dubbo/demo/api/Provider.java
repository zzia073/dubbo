package com.study.dubbo.demo.api;

import com.study.dubbo.demo.DemoService;
import com.study.dubbo.demo.DemoServiceImpl;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) {
        //服务实现
        DemoService demoService = new DemoServiceImpl();
        //当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-provider");
        //注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        //注册的根目录
        registry.setGroup("api");
        //setAddress内部有判断如果url中带有protocol和port会自动解析
        registry.setAddress("127.0.0.1:2181");
        //协议配置，暴露服务用的协议
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setThreads(Runtime.getRuntime().availableProcessors() * 2);
        //ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        //服务提供者配置
        ServiceConfig<DemoService> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setInterface(DemoService.class);
        service.setRef(demoService);
        service.export();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
