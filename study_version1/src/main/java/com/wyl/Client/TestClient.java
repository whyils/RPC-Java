package com.wyl.Client;

import com.wyl.Client.proxy.ClientProxy;
import com.wyl.common.pojo.User;
import com.wyl.common.service.UserService;

/**
 * @author WYL
 * @date 2025/2/7 16:52
 * @description: TODO
 */
public class TestClient {

    public static void main(String[] args) {

        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 9999);
        UserService proxy = clientProxy.getProxy(UserService.class);

        User userByUserId = proxy.getUserByUserId(1);
        System.out.println("从服务端获取的数据user="+userByUserId);

        Integer userId = proxy.insertUserId(User.builder().userName("wyl").id(100).sex(true).build());
        System.out.println("向服务端插入数据后获取的userId="+userId);

    }

}
