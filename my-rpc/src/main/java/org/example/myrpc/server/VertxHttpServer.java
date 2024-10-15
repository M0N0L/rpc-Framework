package org.example.myrpc.server;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class VertxHttpServer implements HttpServer {

    @Override
    public void doStart(int port) {

        Vertx vertx = Vertx.vertx();

        // 通过vertx示例创建Http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
//        server.requestHandler(httpServerRequest -> {
//            // 处理Http请求
//            System.out.println("Received request:" + httpServerRequest.method() + " " + httpServerRequest.uri());
//
//            // 发送Http响应
//            httpServerRequest.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x Http server!");
//
//        });
        server.requestHandler(new HttpServerHandler());

        // 启动Http服务器并监听端口
        server.listen(port, result -> {
            if(result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.err.println("Failed to start server" + result.cause());
            }
        });

    }
}
