package part1.common.serializer.mySerializer;

import com.alibaba.fastjson.JSONObject;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;

public class JsonSerializer implements Serializer{
    @Override
    public byte[] serializer(Object o) {
        return JSONObject.toJSONBytes(o);
    }

    @Override
    public Object deserializer(byte[] bytes, int messageType) {

        Object obj = null;

        switch (messageType) {
            case 0:
                RpcRequest rpcRequest = JSONObject.parseObject(bytes, RpcRequest.class);
                Object[] objects = new Object[rpcRequest.getParamsType().length];
                for (int i = 0; i < objects.length; i++) {

                    Class<?> paramsType = rpcRequest.getParamsType()[i];
                    if (!paramsType.isAssignableFrom(rpcRequest.getParams()[i].getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject)rpcRequest.getParams()[i], paramsType);
                    }else {
                        objects[i] = rpcRequest.getParams()[i];
                    }
                }
                rpcRequest.setParams(objects);
                obj = rpcRequest;
                break;
            case 1:

                RpcResponse rpcResponse = JSONObject.parseObject(bytes, RpcResponse.class);

                Class<?> dataType = rpcResponse.getDataType();
                if (!dataType.isAssignableFrom(rpcResponse.getData().getClass())) {
                    rpcResponse.setData(JSONObject.toJavaObject((JSONObject)rpcResponse.getData(), dataType));
                }
                obj = rpcResponse;
                break;
            default:
                System.out.println("暂时不支持这个消息");
                throw new RuntimeException();
        }

        return obj;
    }

    @Override
    public int getType() {
        return 1;
    }
}
