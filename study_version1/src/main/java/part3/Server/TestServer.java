package part3.Server;


import part3.Server.provider.ServiceProvider;
import part3.Server.server.RpcServer;
import part3.Server.server.impl.NettyRpcServer;
import part3.common.service.UserService;
import part3.common.service.impl.UserServiceImpl;

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

        RpcServer server = new NettyRpcServer(serviceProvider);

        server.start(19999);

    }

}
