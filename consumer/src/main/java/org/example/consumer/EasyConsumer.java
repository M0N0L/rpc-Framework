package org.example.consumer;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.myrpc.bootstrap.ConsumerBootStrap;
import org.example.myrpc.proxy.ServiceProxyFactory;

public class EasyConsumer {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootStrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("mono");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
