package org.example.consumer;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.myrpc.config.RpcConfig;
import org.example.myrpc.proxy.ServiceProxyFactory;
import org.example.myrpc.utils.ConfigUtils;

public class EasyConsumer {
    public static void main(String[] args) {
//        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
//        System.out.println(rpc);
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();

        user.setName("mono");

        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
//       只在mock调用时使用，用于测试Mock
//        short number = userService.getNumber();
//        System.out.println(number);
    }
}
