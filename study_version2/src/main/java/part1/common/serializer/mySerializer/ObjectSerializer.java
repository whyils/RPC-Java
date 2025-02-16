package part1.common.serializer.mySerializer;

import java.io.*;

public class ObjectSerializer implements Serializer{
    @Override
    public byte[] serializer(Object o) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();

            bytes = bos.toByteArray();
            bos.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    @Override
    public Object deserializer(byte[] bytes, int messageType) {

        Object obj = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);

            obj = ois.readObject();

            bis.close();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public int getType() {
        return 0;
    }
}
