package part2.Client.rpcClient;


import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

/**
 * @author WYL
 * @date 2025/2/8 16:15
 * @description: TODO
 */
public interface RpcClient {

    RpcResponse sentRequest(RpcRequest request);

}
