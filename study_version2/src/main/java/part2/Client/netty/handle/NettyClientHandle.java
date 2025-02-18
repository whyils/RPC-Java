package part2.Client.netty.handle;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import part2.common.Message.RpcResponse;

/**
 * @author WYL
 * @date 2025/2/8 16:35
 * @description: TODO
 */
public class NettyClientHandle extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {

        AttributeKey<RpcResponse> attr = AttributeKey.valueOf("RPCResponse");
        ctx.channel().attr(attr).set(rpcResponse);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
