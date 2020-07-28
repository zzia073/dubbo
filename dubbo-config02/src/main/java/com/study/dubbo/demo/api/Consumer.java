package com.study.dubbo.demo.api;

import com.study.dubbo.demo.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

public class Consumer {
    public static void main(String[] args) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-consumer");
        application.setQosEnable(false);
        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol("zookeeper");
        registry.setAddress("127.0.0.1:2181");
        registry.setGroup("api");

        // 注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接

        // 引用远程服务
        ReferenceConfig<DemoService> reference = new ReferenceConfig<DemoService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
        reference.setApplication(application);
        reference.setRegistry(registry); // 多个注册中心可以用setRegistries()
        reference.setInterface(DemoService.class);

        // 和本地bean一样使用xxxService
        DemoService demoService = reference.get();
        String sayHello = demoService.sayHello("dubbo");
        System.out.println(sayHello);
    }
}
