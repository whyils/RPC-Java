package part3.Client.proxy;

import part3.Client.rpcClient.RpcClient;
import part3.Client.rpcClient.impl.NettyRpcClient;
import part3.Client.rpcClient.impl.SimpleSocketRpcClient;
import part3.common.Message.RpcRequest;
import part3.common.Message.RpcResponse;

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

    public ClientProxy(String host, Integer port, int choose) {

        switch (choose) {
            case 0:
                rpcClient = new NettyRpcClient();
                break;
            case 1:
                rpcClient = new SimpleSocketRpcClient(host, port);
                break;
        }

    }
    public ClientProxy() {
        rpcClient = new NettyRpcClient();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();

        RpcResponse rpcResponse = rpcClient.sentRequest(rpcRequest);

        return rpcResponse.getData();
    }

    public <T>T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
