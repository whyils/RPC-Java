package part1.Client.rpcClient;


import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

/**
 * @author WYL
 * @date 2025/2/8 16:15
 * @description: TODO
 */
public interface RpcClient {

    RpcResponse sentRequest(RpcRequest request);

}
