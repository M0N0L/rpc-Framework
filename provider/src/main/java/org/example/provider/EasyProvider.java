package org.example.provider;

import org.example.common.service.UserService;
import org.example.myrpc.RpcApplication;
import org.example.myrpc.registry.LocalRegistry;
import org.example.myrpc.server.HttpServer;
import org.example.myrpc.server.VertxHttpServer;

public class EasyProvider {
    public static void main(String[] args) {
        //初始化RPC框架
        RpcApplication.init();
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        //提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
