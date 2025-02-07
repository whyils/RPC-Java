package com.wyl.Client.proxy;

import com.wyl.Client.IOClient;
import com.wyl.common.Message.RpcRequest;
import com.wyl.common.Message.RpcResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author WYL
 * @date 2025/2/7 16:32
 * @description: TODO
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {

    private String host;
    private Integer port;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramsType(method.getParameterTypes()).build();

        RpcResponse rpcResponse = IOClient.sentRequest(host, port, rpcRequest);

        return rpcResponse.getCode();
    }

    public <T>T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
