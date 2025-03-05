package com.wyl.Server.server.impl;


import lombok.AllArgsConstructor;
import com.wyl.Server.provider.ServiceProvider;
import com.wyl.Server.server.RpcServer;
import com.wyl.Server.server.work.WorkThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author WYL
 * @date 2025/2/8 14:49
 * @description: TODO
 */
@AllArgsConstructor
public class SimpleRpcServer implements RpcServer {

    private ServiceProvider serviceProvider;

    @Override
    public void start(int port) {

        try {
            ServerSocket socket = new ServerSocket(port);
            System.out.println("服务器启动啦");
            while (true) {
                Socket accept = socket.accept();
                new Thread(new WorkThread(accept, serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {

    }
}
