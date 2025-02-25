package part1.common.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author WYL
 * @date 2025/2/7 15:57
 * @description: TODO
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RpcResponse implements Serializable {

    private int code;
    private String message;
    //更新：加入传输数据的类型，以便在自定义序列化器中解析
    private Class<?> dataType;
    private Object data;

    public static RpcResponse success(Object data) {
        return RpcResponse.builder().dataType(data.getClass()).code(200).data(data).build();
    }

    public static RpcResponse fail() {
        return RpcResponse.builder().code(500).message("服务器发生错误").build();
    }

}
