package part3.Client.rpcClient.impl;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import part3.Client.netty.nettyInitializer.NettyClientInitializer;
import part3.Client.rpcClient.RpcClient;
import part3.common.Message.RpcRequest;
import part3.common.Message.RpcResponse;

/**
 * @author WYL
 * @date 2025/2/10 10:36
 * @description: TODO
 */
public class NettyRpcClient implements RpcClient {

    private String host;
    private int port;
    private static final Bootstrap bootstarp;
    private static final EventLoopGroup eventLoopGroup;

    public NettyRpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstarp = new Bootstrap();
        bootstarp.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
    }

    @Override
    public RpcResponse sentRequest(RpcRequest request) {

        try {
            ChannelFuture channelFuture = bootstarp.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            channel.writeAndFlush(request);
            channel.closeFuture().sync();

            AttributeKey<RpcResponse> attributeKey = AttributeKey.valueOf("RPCResponse");
            RpcResponse rpcResponse = channel.attr(attributeKey).get();
            System.out.println(rpcResponse);
            return rpcResponse;

        } catch (InterruptedException e) {
            e.printStackTrace();

        }

        return null;
    }
}
