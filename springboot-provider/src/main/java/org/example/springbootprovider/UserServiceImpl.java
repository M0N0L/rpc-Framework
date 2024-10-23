package org.example.springbootprovider;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.myrpcspringbootstarter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("收到远程调用，用户名：" + user.getName());
        return user;
    }
}
