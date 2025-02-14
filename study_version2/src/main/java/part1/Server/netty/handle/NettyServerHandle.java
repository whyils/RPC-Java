package part1.Server.netty.handle;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import part1.Server.provider.ServiceProvider;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author WYL
 * @date 2025/2/10 11:12
 * @description: TODO
 */
@AllArgsConstructor
public class NettyServerHandle extends SimpleChannelInboundHandler<RpcRequest> {

    private ServiceProvider serviceProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest request) throws Exception {

        RpcResponse response = getResponse(request);
        ctx.writeAndFlush(response);
        ctx.close();

    }

    private RpcResponse getResponse(RpcRequest request) {

        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);

        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsType());
            Object invoke = method.invoke(service, request.getParams());
            return RpcResponse.success(invoke);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return RpcResponse.fail();
        }
    }

}
