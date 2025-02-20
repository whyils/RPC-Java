package part1.common.serializer.mySerializer;

public interface Serializer {

    // 将对象序列化为字符数组
    byte[] serializer(Object o);

    // 将字节数组反序列化为对象根据消息格式
    Object deserializer(byte[] bytes, int messageType);

    // 返回使用的序列化器
    int getType();

    // 根据给定编码返回对应的序列化对象
    static Serializer getSerializerByCode(int code) {
        switch (code) {
            case 0:
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
        }
        return null;
    }

}
