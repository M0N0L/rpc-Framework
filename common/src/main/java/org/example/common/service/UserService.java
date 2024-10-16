package org.example.common.service;

import org.example.common.model.User;

public interface UserService {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    default short getNumber() {
        return 100;
    }
}
