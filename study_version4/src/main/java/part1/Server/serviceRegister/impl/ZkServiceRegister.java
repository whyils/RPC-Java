package part1.Server.serviceRegister.impl;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import part1.Server.serviceRegister.ServiceRegister;

import java.net.InetSocketAddress;

/**
 * @author WYL
 * @date 2025/2/12 10:51
 * @description: TODO
 */
public class ZkServiceRegister implements ServiceRegister {

    private CuratorFramework client;
    private static final String ROOT_PATH = "MyRPC";
    private static final String RETRY = "CanRetry";

    public ZkServiceRegister() {
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181").retryPolicy(policy)
                .sessionTimeoutMs(40000).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zk连接成功");
    }

    @Override
    public void register(String serviceName, InetSocketAddress socketAddress, boolean canRetry) {

        try {
            if (client.checkExists().forPath("/" + serviceName) == null) {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            String path = "/" + serviceName + "/" + getServiceAddress(socketAddress);
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);

            if (canRetry) {
                path = "/" + RETRY + "/" + serviceName;
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("此服务已存在");
        }

    }

    private String getServiceAddress(InetSocketAddress socketAddress) {
        return socketAddress.getHostName() + ":" + socketAddress.getPort();
    }

    private InetSocketAddress parseAddress(String address) {
        String[] split = address.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }

}
