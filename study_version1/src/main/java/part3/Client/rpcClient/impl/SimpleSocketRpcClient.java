package part3.Client.rpcClient.impl;


import lombok.AllArgsConstructor;
import part3.Client.rpcClient.RpcClient;
import part3.common.Message.RpcRequest;
import part3.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author WYL
 * @date 2025/2/8 16:18
 * @description: TODO
 */
@AllArgsConstructor
public class SimpleSocketRpcClient implements RpcClient {

    private String host;
    private int port;


    @Override
    public RpcResponse sentRequest(RpcRequest request) {

        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject(request);
            oos.flush();

            RpcResponse rpcResponse = (RpcResponse) ois.readObject();
            return rpcResponse;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
