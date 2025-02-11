package part1.Server;


import part1.Server.provider.ServiceProvider;
import part1.Server.server.RpcServer;
import part1.Server.server.impl.ThreadPoolRpcServer;
import part1.common.service.UserService;
import part1.common.service.impl.UserServiceImpl;

/**
 * @author WYL
 * @date 2025/2/8 15:15
 * @description: TODO
 */
public class TestServer {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);

        RpcServer server = new ThreadPoolRpcServer(serviceProvider);

        server.start(19999);

    }

}
