package part1.Server.server.impl;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import part1.Server.netty.nettyInitializer.NettyServerInitializer;
import part1.Server.provider.ServiceProvider;
import part1.Server.server.RpcServer;

/**
 * @author WYL
 * @date 2025/2/10 14:39
 * @description: TODO
 */
@AllArgsConstructor
public class NettyRpcServer implements RpcServer {

    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        System.out.println("netty服务器启动");
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyServerInitializer(serviceProvider));

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {

    }
}
