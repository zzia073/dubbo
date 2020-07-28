package com.study.zookeeper.registry;

import org.apache.zookeeper.*;

/**
 * dubbo协议URL形如：
 * dubbo://192.168.1.3:20880/com.study.dubbo.org.samples.api.GreetingsService?
 * anyhost=true&application=first-dubbo-provider&default=true&deprecated=false
 * &dubbo=2.0.2&dynamic=true&generic=false&interface=com.study.dubbo.org.samples.api.GreetingsService
 * &methods=introduceSelf,sayHi&pid=11732&release=2.7.7&side=provider&timestamp=1595854677656
 *
 */
public class Provider {
    public void export() throws Exception{
        ZooKeeper zkCli = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {

            }
        });
        zkCli.create("/dubbo", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
}
