package part1.common.Message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author WYL
 * @date 2025/2/7 15:52
 * @description: TODO
 */
@Data
@Builder
public class RpcRequest implements Serializable {

    // 接口名
    private String interfaceName;

    // 方法名
    private String methodName;

    // 参数列表
    private Object[] params;

    // 参数类型
    private Class<?>[] paramsType;

}
