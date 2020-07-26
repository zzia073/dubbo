package com.study.zookeeper.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestZookeeper {
    private static final String connectString = "192.168.1.251:2181";
    private ZooKeeper zkCli;
    private Map<String, String> upServers = new HashMap<>();
    //连接zk
    @Before
    @Test
    public void init() throws Exception{
        zkCli = new ZooKeeper(connectString, 500000, new Watcher() {
            public void process(WatchedEvent event) {
//                System.out.println("connect success ...");
//
//                try {
//                    List<String> children = zkCli.getChildren("/", true);
//                    children.forEach(System.out::println);
//                } catch (KeeperException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

    }
    //创建节点
    @Test
    public void createNode() throws Exception {
        String path = zkCli.create("/sanguo/weiguo", "caocao".getBytes("UTF-8"),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }
    //获取节点并监听节点变化
    @Test
    public void getNodeAndWatch() throws Exception {
        List<String> children = zkCli.getChildren("/", true);
//        children.forEach(System.out::println);
        for (;;) {

        }
    }
    //判断节点是否存在
    @Test
    public void existNode() throws Exception {
        Stat stat = zkCli.exists("/sanguo", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("event happened");
            }
        });
        for(;;){

        }
    }
    //动态判定服务器上下线
    @Test
    public void upAndDownServer() throws Exception {
        Stat stat = zkCli.exists("/server", false);
        if (stat == null) {
            zkCli.create("/server", "upAndDownServer".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        watchServer(zkCli);
        for (;;) {

        }
    }
    private void watchServer(ZooKeeper zkCli) throws Exception {
        List<String> children = zkCli.getChildren("/server", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                try {
                    watchServer(zkCli);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        children.forEach(node -> {
            String path = "/server/" + node;
            if (!upServers.containsKey(path)) {
                try {
                    watchChildNode(zkCli, node);
                    byte[] data = zkCli.getData(path, false, zkCli.exists(path, false));
                    upServers.put(path, new String(data));
                    reportStatus(upServers.get(path), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void watchChildNode(ZooKeeper zkCli, String node) throws Exception {
        zkCli.exists("/server/" + node, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                String path = event.getPath();
                if (Event.EventType.NodeDeleted.equals(event.getType())){
                    String server = upServers.get(path);
                    upServers.remove(path);
                    reportStatus(server, false);
                } else {
                    try {
                        watchChildNode(zkCli, node);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void reportStatus(String server, boolean status) {
        System.out.println(server + (status ? " up" : " down"));
    }
}
