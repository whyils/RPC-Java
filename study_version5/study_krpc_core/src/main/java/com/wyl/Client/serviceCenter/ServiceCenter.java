package com.wyl.Client.serviceCenter;


import java.net.InetSocketAddress;

/**
 * @author WYL
 * @date 2025/2/11 16:52
 * @description: TODO
 */
public interface ServiceCenter {

    InetSocketAddress serviceDiscovery(String serviceName);

    // 检查当前服务是否可以重试
    boolean checkRetry(String serviceName);

}
