package com.wyl.Client;

import com.wyl.Message.RpcRequest;
import com.wyl.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author WYL
 * @date 2025/2/7 16:06
 * @description: TODO
 */
public class IOClient {

    public static RpcResponse sentRequest(String host, int port, RpcRequest request) {

        try {
            Socket socket = new Socket(host, port);

            // 获取socket对象输出流，将对象序列化之后发送到服务端
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 获取socket对象输入流，将从服务端发送过来的数据进行反序列化
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
