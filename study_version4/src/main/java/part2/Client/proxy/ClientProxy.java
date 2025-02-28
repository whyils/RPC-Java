package part2.Client.proxy;

import part2.Client.circuitBreaker.CircuitBreaker;
import part2.Client.circuitBreaker.CircuitBreakerProvider;
import part2.Client.retry.GuavaRetry;
import part2.Client.rpcClient.RpcClient;
import part2.Client.rpcClient.impl.NettyRpcClient;
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
    private CircuitBreakerProvider circuitBreakerProvider;

    public ClientProxy() throws InterruptedException {
        serviceCenter = new ZkServiceCenter();
        rpcClient = new NettyRpcClient(serviceCenter);
        circuitBreakerProvider = new CircuitBreakerProvider();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();

        // 熔断器不允许访问，就返回
        CircuitBreaker circuitBreaker = circuitBreakerProvider.getCircuitBreaker(method.getName());
        if (!circuitBreaker.allowRequest()) {
            System.out.println("熔断器不允许访问");
            return null;
        }

        RpcResponse rpcResponse;
        if (serviceCenter.checkRetry(rpcRequest.getInterfaceName())) {
            rpcResponse = new GuavaRetry().sendServiceWithRetry(rpcRequest, rpcClient);
        }else {
            rpcResponse = rpcClient.sentRequest(rpcRequest);
        }
        if (rpcResponse == null || rpcResponse.getCode() != 200) {
            circuitBreaker.recordFailure();
            return null;
        }
        circuitBreaker.recordSuccess();

        return rpcResponse.getData();
    }

    public <T>T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
