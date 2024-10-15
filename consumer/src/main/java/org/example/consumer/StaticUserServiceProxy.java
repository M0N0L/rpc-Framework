package org.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.example.common.model.User;
import org.example.common.service.UserService;
import org.example.myrpc.model.RpcRequest;
import org.example.myrpc.model.RpcResponse;
import org.example.myrpc.serializer.JdkSerializer;
import org.example.myrpc.serializer.Serializer;

import java.io.IOException;

public class StaticUserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder().serviceName(UserService.class
                .getName()).methodName("getUser").parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;

            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
