package part2.Server.netty.handle;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import part2.Server.provider.ServiceProvider;
import part2.Server.ratelimit.RateLimit;
import part2.Server.ratelimit.provider.RateLimitProvider;
import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

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

        RateLimitProvider rateLimitProvider = serviceProvider.getRateLimitProvider();
        RateLimit rateLimit = rateLimitProvider.getRateLimit(interfaceName);
        if (!rateLimit.getToken()){
            System.out.println(interfaceName + "服务被限流！！");
            return RpcResponse.fail();
        }

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
