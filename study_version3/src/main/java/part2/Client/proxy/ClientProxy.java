package part2.Client.proxy;

import part2.Client.cache.ServiceCache;
import part2.Client.retry.GuavaRetry;
import part2.Client.rpcClient.RpcClient;
import part2.Client.rpcClient.impl.NettyRpcClient;
import part2.Client.rpcClient.impl.SimpleSocketRpcClient;
import part2.Client.serviceCenter.ServiceCenter;
import part2.Client.serviceCenter.impl.ZkServiceCenter;
import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author WYL
 * @date 2025/2/7 16:32
 * @description: TODO
 */
public class ClientProxy implements InvocationHandler {

    private RpcClient rpcClient;
    private ServiceCenter serviceCenter;

    public ClientProxy() throws InterruptedException {
        serviceCenter = new ZkServiceCenter();
        rpcClient = new NettyRpcClient(serviceCenter);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();

        RpcResponse rpcResponse;
        if (serviceCenter.checkRetry(rpcRequest.getInterfaceName())) {
            rpcResponse = new GuavaRetry().sendServiceWithRetry(rpcRequest, rpcClient);
        }else {
            rpcResponse = rpcClient.sentRequest(rpcRequest);
        }

        return rpcResponse.getData();
    }

    public <T>T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
