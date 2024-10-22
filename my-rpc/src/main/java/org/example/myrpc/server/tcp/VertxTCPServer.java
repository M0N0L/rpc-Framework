package org.example.myrpc.server.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import org.example.myrpc.server.HttpServer;

public class VertxTCPServer implements HttpServer {


    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        NetServer server = vertx.createNetServer();
        server.connectHandler(new TCPServerHandler());

        server.listen(port, result -> {
            if(result.succeeded()) {
                System.out.println("TCP server started on port " + port);
            } else {
                System.out.println("Failed to start TCP server" + result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTCPServer().doStart(8888);
    }
}
