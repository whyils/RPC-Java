package part2.Server.server.work;


import lombok.AllArgsConstructor;
import part2.Server.provider.ServiceProvider;
import part2.common.Message.RpcRequest;
import part2.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author WYL
 * @date 2025/2/8 14:12
 * @description: TODO
 */

@AllArgsConstructor
public class WorkThread implements Runnable{

    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
            RpcResponse rpcResponse = getResponse(rpcRequest);

            oos.writeObject(rpcResponse);
            oos.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private RpcResponse getResponse(RpcRequest request) {

        String interfaceName = request.getInterfaceName();
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
