package part2.common.serializer.myCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import part2.common.Message.MessageType;
import part2.common.serializer.mySerializer.Serializer;

import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 读消息类型
        short messageType = in.readShort();

        if (messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()) {
            System.out.println("暂时不支持相关类型");
            throw new RuntimeException("暂时不支持相关类型");
        }

        // 获取序列化类型
        short serializerType = in.readShort();
        // 通过序列化类型获取对应的序列化对象
        Serializer serializerByCode = Serializer.getSerializerByCode(serializerType);
        if (serializerByCode == null) {
            throw new RuntimeException("不存在对应的序列化类型");
        }

        short length = in.readShort();
        byte[] bytes = new byte[length];

        in.readBytes(bytes);

        Object deserializer = serializerByCode.deserializer(bytes, messageType);
        out.add(deserializer);

    }
}
