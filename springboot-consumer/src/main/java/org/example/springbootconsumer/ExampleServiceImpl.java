package org.example.springbootconsumer;

import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.myrpcspringbootstarter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {
        @RpcReference
        private UserService userService;

        public void test() {
            User user = new User();
            user.setName("mono");
            User resultUser = userService.getUser(user);
            System.out.println(resultUser.getName());
        }
}
