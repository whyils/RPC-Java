package part2.Server.provider;


import part2.Server.ratelimit.provider.RateLimitProvider;
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
    private RateLimitProvider rateLimitProvider;

    public ServiceProvider() {
        interfaceProvider = new HashMap<>();
    }

    public ServiceProvider(int port, String host) {
        this.port = port;
        this.host = host;
        this.serviceRegister = new ZkServiceRegister();
        this.interfaceProvider = new HashMap<>();
        this.rateLimitProvider = new RateLimitProvider();
    }

    public void provideServiceInterface(Object server, boolean canRetry) {
        String name = server.getClass().getName();
        Class<?>[] interfaces = server.getClass().getInterfaces();

        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), server);
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port), canRetry);
        }

    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }

    public RateLimitProvider getRateLimitProvider() {
        return rateLimitProvider;
    }



}
