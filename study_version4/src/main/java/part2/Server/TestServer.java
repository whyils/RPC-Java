package part2.Server;


import part2.Server.provider.ServiceProvider;
import part2.Server.server.RpcServer;
import part2.Server.server.impl.NettyRpcServer;
import part2.common.service.UserService;
import part2.common.service.impl.UserServiceImpl;

/**
 * @author WYL
 * @date 2025/2/8 15:15
 * @description: TODO
 */
public class TestServer {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider(20000, "127.0.0.1");
        serviceProvider.provideServiceInterface(userService, true);

        RpcServer server = new NettyRpcServer(serviceProvider);

        server.start(20000);

    }

}
