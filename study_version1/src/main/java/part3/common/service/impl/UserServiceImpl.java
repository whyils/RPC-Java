package part3.common.service.impl;

import part3.common.pojo.User;
import part3.common.service.UserService;

import java.util.Random;
import java.util.UUID;

/**
 * @author WYL
 * @date 2025/2/7 15:43
 * @description: TODO
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {

        System.out.println("客户端查询的用户ID：" + id + "的用户");

        User user = User.builder().id(id).userName(UUID.randomUUID().toString()).sex(new Random().nextBoolean()).build();

        return user;
    }

    @Override
    public Integer insertUserId(User user) {

        System.out.println("插入数据成功"+user.getUserName());

        return user.getId();
    }
}
