package org.example.myrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import lombok.extern.slf4j.Slf4j;
import org.example.myrpc.RpcApplication;
import org.example.myrpc.config.RpcConfig;
import org.example.myrpc.constant.ProtocolConstant;
import org.example.myrpc.constant.RetryStrategyKeys;
import org.example.myrpc.constant.RpcConstant;
import org.example.myrpc.fault.retry.RetryStrategy;
import org.example.myrpc.fault.retry.RetryStrategyFactory;
import org.example.myrpc.loadbalancer.LoadBalancer;
import org.example.myrpc.loadbalancer.LoadBalancerFactory;
import org.example.myrpc.model.RpcRequest;
import org.example.myrpc.model.RpcResponse;
import org.example.myrpc.model.ServiceMetaInfo;
import org.example.myrpc.protocol.*;
import org.example.myrpc.registry.Registry;
import org.example.myrpc.registry.RegistryFactory;
import org.example.myrpc.serializer.Serializer;
import org.example.myrpc.serializer.SerializerFactory;
import org.example.myrpc.server.tcp.VertxTcpClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            //负载均衡
            LoadBalancer balancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());

            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = balancer.select(requestParams, serviceMetaInfoList);
            //Http 请求
//            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
//                    .body(bodyBytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
//                return rpcResponse.getData();
//            }
            // 发送TCP请求
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            RpcResponse rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo));
            return rpcResponse.getData();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
