package com.wyl.consumer;

import com.wyl.Client.proxy.ClientProxy;
import com.wyl.pojo.User;
import com.wyl.service.UserService;

/**
 * @author WYL
 * @date 2025/2/7 16:52
 * @description: TODO
 */
public class TestClient {

    public static void main(String[] args) throws InterruptedException {

        ClientProxy clientProxy = new ClientProxy();
        UserService proxy = clientProxy.getProxy(UserService.class);

        for (int i = 0; i < 120; i++) {

            int j = i;

            new Thread(() -> {

                try {
                    User userByUserId = proxy.getUserByUserId(j);
                    System.out.println("从服务端获取的数据user="+userByUserId);

                    Integer userId = proxy.insertUserId(User.builder().userName("wyl").id(j).sex(true).build());
                    System.out.println("向服务端插入数据后获取的userId="+userId);
                }catch (NullPointerException e) {
                    System.out.println("user=null");
                    e.printStackTrace();
                }

            }).start();

            if (i >= 30 && i % 30 == 0) {
                Thread.sleep(10000);
            }

        }

//        User userByUserId = proxy.getUserByUserId(1);
//        System.out.println("从服务端获取的数据user="+userByUserId);
//
//        Integer userId = proxy.insertUserId(User.builder().userName("wyl").id(100).sex(true).build());
//        System.out.println("向服务端插入数据后获取的userId="+userId);

    }

}
