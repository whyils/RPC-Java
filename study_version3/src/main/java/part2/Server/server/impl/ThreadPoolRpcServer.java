package part2.Server.server.impl;


import part2.Server.provider.ServiceProvider;
import part2.Server.server.RpcServer;
import part2.Server.server.work.WorkThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author WYL
 * @date 2025/2/8 14:56
 * @description: TODO
 */
public class ThreadPoolRpcServer implements RpcServer {

    private ThreadPoolExecutor threadPoolExecutor;
    private ServiceProvider serviceProvider;

    public ThreadPoolRpcServer(ServiceProvider serviceProvider) {

        this.serviceProvider = serviceProvider;
        this.threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 1000, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }

    public ThreadPoolRpcServer(ServiceProvider serviceProvider, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this.serviceProvider = serviceProvider;
        this.threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }


    @Override
    public void start(int port) {
        System.out.println("服务端启动啦");
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket accept = socket.accept();
                threadPoolExecutor.execute(new WorkThread(accept, serviceProvider));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {

    }
}
