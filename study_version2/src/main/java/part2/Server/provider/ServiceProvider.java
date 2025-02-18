package part2.Server.provider;


import part2.Server.serviceRegister.ServiceRegister;
import part2.Server.serviceRegister.impl.ZkServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WYL
 * @date 2025/2/7 17:14
 * @description: TODO
 */
public class ServiceProvider {

    private Map<String, Object> interfaceProvider;

    private int port;
    private String host;
    private ServiceRegister serviceRegister;

    public ServiceProvider() {
        interfaceProvider = new HashMap<>();
    }

    public ServiceProvider(int port, String host) {
        this.port = port;
        this.host = host;
        this.serviceRegister = new ZkServiceRegister();
        this.interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object server) {
        String name = server.getClass().getName();
        Class<?>[] interfaces = server.getClass().getInterfaces();

        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), server);
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }

    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }



}
