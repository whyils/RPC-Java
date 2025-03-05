package com.wyl.Client.rpcClient;


import com.wyl.Message.RpcRequest;
import com.wyl.Message.RpcResponse;

/**
 * @author WYL
 * @date 2025/2/8 16:15
 * @description: TODO
 */
public interface RpcClient {

    RpcResponse sentRequest(RpcRequest request);

}
