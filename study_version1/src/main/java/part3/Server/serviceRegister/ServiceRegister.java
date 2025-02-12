package part3.Server.serviceRegister;


import java.net.InetSocketAddress;

/**
 * @author WYL
 * @date 2025/2/12 10:49
 * @description: TODO
 */
public interface ServiceRegister {

    void register(String serviceName, InetSocketAddress socketAddress);

}
