package org.example.provider;

import org.example.common.service.UserService;
import org.example.myrpc.registry.LocalRegistry;
import org.example.myrpc.server.HttpServer;
import org.example.myrpc.server.VertxHttpServer;

public class EasyProvider {
    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        //提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
