package org.example.myrpc.server.tcp;

import io.vertx.core.Vertx;

public class VertxTCPClient {
    public void start() {
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8888, "localhost", result -> {
            if(result.succeeded()) {
                System.out.println("Connect to TCP server");
                io.vertx.core.net.NetSocket socket = result.result();

                socket.write("Hello, server!");

                socket.handler(buffer -> {
                    System.out.println("Reveived Response from server:" + buffer.toString());
                });
            } else {
                System.err.println("Failed to connect to TCP server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTCPClient().start();
    }
}
