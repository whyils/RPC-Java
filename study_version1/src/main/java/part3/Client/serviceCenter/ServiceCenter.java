package part3.Client.serviceCenter;


import java.net.InetSocketAddress;

/**
 * @author WYL
 * @date 2025/2/11 16:52
 * @description: TODO
 */
public interface ServiceCenter {

    InetSocketAddress serviceDiscovery(String serviceName);

}
