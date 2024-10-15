package org.example.consumer;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.myrpc.proxy.ServiceProxyFactory;

public class EasyConsumer {
    public static void main(String[] args) {
//        UserService userService = new StaticUserServiceProxy();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();

        user.setName("mono");

        User newUser = userService.getUser(user);
        if(newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
