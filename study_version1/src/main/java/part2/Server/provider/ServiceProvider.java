package part2.Server.provider;


import java.util.HashMap;
import java.util.Map;

/**
 * @author WYL
 * @date 2025/2/7 17:14
 * @description: TODO
 */
public class ServiceProvider {

    private Map<String, Object> interfaceProvider;

    public ServiceProvider() {
        interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object server) {
        String name = server.getClass().getName();
        Class<?>[] interfaces = server.getClass().getInterfaces();

        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), server);
        }

    }

    public Object getService(String interfaceName) {
        return interfaceProvider.get(interfaceName);
    }



}
