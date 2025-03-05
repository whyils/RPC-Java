package com.wyl.consumer;

import com.wyl.Client.proxy.ClientProxy;
import com.wyl.pojo.User;
import com.wyl.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author WYL
 * @date 2025/2/7 16:52
 * @description: TODO
 */
@Slf4j
public class ConsumerTest {

    private static final int THREAD_POOL_SIZE = 20;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) throws InterruptedException {

        ClientProxy clientProxy = new ClientProxy();
        UserService proxy = clientProxy.getProxy(UserService.class);

        for (int i = 0; i < 120; i++) {

            int j = i;

            int finalI = i;
            executorService.submit(() -> {

                try {
                    User userByUserId = proxy.getUserByUserId(j);
                    if (userByUserId == null) {
                        log.warn("从服务端获取的userByUserId 为 null, userId = {}", finalI);
                    }else {
                        log.info("从服务端获取的数据user={}", userByUserId);
                    }

                    Integer userId = proxy.insertUserId(User.builder().userName("wyl").id(finalI).sex(true).build());
                    if (userId == null) {
                        log.warn("从服务端获取的userId 为 null, userId = {}", finalI);
                    }else {
                        log.info("从服务端获取的数据userId={}", userId);
                    }
                }catch (NullPointerException e) {
                    log.error("调用服务时发生异常，userId = {}", finalI, e);
                }

            });

            if (i >= 30 && i % 30 == 0) {
                Thread.sleep(10000);
            }

        }
        executorService.shutdown();

    }

}
