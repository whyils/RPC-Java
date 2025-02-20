package part1.common.serializer.myCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import part1.common.Message.MessageType;
import part1.common.Message.RpcRequest;
import part1.common.Message.RpcResponse;
import part1.common.serializer.mySerializer.Serializer;

@AllArgsConstructor
public class MyEncoder extends MessageToByteEncoder {

    private Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        System.out.println(o.getClass());

        // 判断消息是Request或者Response，根据类型写入类型参数
        if (o instanceof RpcRequest) {
            byteBuf.writeShort(MessageType.REQUEST.getCode());
        } else if (o instanceof RpcResponse) {
            byteBuf.writeShort(MessageType.RESPONSE.getCode());
        }

        // 将当前序列化的类型写入
        byteBuf.writeShort(serializer.getType());
        byte[] bytes = serializer.serializer(o);

        // 将字节长度写入
        byteBuf.writeShort(bytes.length);
        // 将字节数据写入
        byteBuf.writeBytes(bytes);


    }
}
