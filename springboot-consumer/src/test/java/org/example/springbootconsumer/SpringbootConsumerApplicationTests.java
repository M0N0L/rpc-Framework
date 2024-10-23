package org.example.springbootconsumer;

import jakarta.annotation.Resource;
import org.example.common.service.UserService;
import org.example.myrpcspringbootstarter.annotation.RpcReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootConsumerApplicationTests {

   @Resource
   private  ExampleServiceImpl exampleService;

    @Test
    public void serviceTest() {
        exampleService.test();
    }


}
