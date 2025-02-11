package part3.Client.serviceCenter.impl;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import part3.Client.serviceCenter.ServiceCenter;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author WYL
 * @date 2025/2/11 16:59
 * @description: TODO
 */
public class ZkServiceCenter implements ServiceCenter {

    private CuratorFramework clent;
    private static final String ROOT_PATH = "MyRPC";

    public ZkServiceCenter() {

        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        this.clent = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
                .retryPolicy(policy).sessionTimeoutMs(40000).namespace(ROOT_PATH).build();
        this.clent.start();
        System.out.println("zk启动成功");

    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {

        try {
            List<String> forPath = this.clent.getChildren().forPath("/" + ROOT_PATH);
            String path = forPath.get(0);
            InetSocketAddress inetSocketAddress = parseAddress(path);
            return inetSocketAddress;
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
