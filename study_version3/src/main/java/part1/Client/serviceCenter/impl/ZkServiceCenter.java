package part1.Client.serviceCenter.impl;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import part1.Client.cache.ServiceCache;
import part1.Client.serviceCenter.ServiceCenter;
import part1.Client.serviceCenter.ZKWatcher.WatchZK;
import part1.Client.serviceCenter.balance.impl.ConsistencyHashLoadBalance;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author WYL
 * @date 2025/2/11 16:59
 * @description: TODO
 */
public class ZkServiceCenter implements ServiceCenter {

    private CuratorFramework client;
    private static final String ROOT_PATH = "MyRPC";
    private ServiceCache cache;

    public ZkServiceCenter() throws InterruptedException {

        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .retryPolicy(policy).sessionTimeoutMs(40000).namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("zk连接成功");

        this.cache = new ServiceCache();
        WatchZK watchZK = new WatchZK(client, this.cache);
        watchZK.watchToUpdate(ROOT_PATH);

    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {

        try {

            List<String> serviceList = cache.getServiceByCache(serviceName);
            if (serviceList == null) {
                serviceList = this.client.getChildren().forPath("/" + serviceName);
            }
            String service = new ConsistencyHashLoadBalance().balance(serviceList);
            return parseAddress(service);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    private String getServiceAddress(InetSocketAddress socketAddress) {
        return socketAddress.getHostName() + ":" + socketAddress.getPort();
    }

    private InetSocketAddress parseAddress(String path) {

        String[] split = path.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));
    }
}
