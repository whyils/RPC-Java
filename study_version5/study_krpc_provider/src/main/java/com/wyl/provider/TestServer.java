package com.wyl.provider;


import com.wyl.Server.provider.ServiceProvider;
import com.wyl.Server.server.RpcServer;
import com.wyl.Server.server.impl.NettyRpcServer;
import com.wyl.service.UserService;
import com.wyl.provider.impl.UserServiceImpl;

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
