package com.wyl.common.service;

import com.wyl.common.pojo.User;

/**
 * @author WYL
 * @date 2025/2/7 15:40
 * @description: TODO
 */
public interface UserService {

    User getUserByUserId(Integer id);

    Integer insertUserId(User user);

}
