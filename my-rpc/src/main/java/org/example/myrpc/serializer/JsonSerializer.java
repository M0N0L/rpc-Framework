package org.example.myrpc.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.myrpc.model.RpcRequest;
import org.example.myrpc.model.RpcResponse;

import java.io.IOException;

public class JsonSerializer implements Serializer{

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        T object = OBJECT_MAPPER.readValue(bytes, type);
        if(object instanceof RpcRequest) {
            return handleRequest((RpcRequest) object, type);
        }
        if(object instanceof RpcResponse) {
            return handleResponse((RpcResponse) object, type);
        }
        return object;
    }

    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        for(int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            if(!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes,clazz);
            }
        }
        return type.cast(rpcRequest);
    }

    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}
