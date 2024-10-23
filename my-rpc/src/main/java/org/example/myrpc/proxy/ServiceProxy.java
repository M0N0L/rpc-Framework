package org.example.myrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import org.example.myrpc.RpcApplication;
import org.example.myrpc.config.RpcConfig;
import org.example.myrpc.constant.RpcConstant;
import org.example.myrpc.fault.retry.RetryStrategy;
import org.example.myrpc.fault.retry.RetryStrategyFactory;
import org.example.myrpc.fault.tolerant.TolerantStrategy;
import org.example.myrpc.fault.tolerant.TolerantStrategyFactory;
import org.example.myrpc.loadbalancer.LoadBalancer;
import org.example.myrpc.loadbalancer.LoadBalancerFactory;
import org.example.myrpc.model.RpcRequest;
import org.example.myrpc.model.RpcResponse;
import org.example.myrpc.model.ServiceMetaInfo;
import org.example.myrpc.registry.Registry;
import org.example.myrpc.registry.RegistryFactory;
import org.example.myrpc.server.tcp.VertxTcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();


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
        // 将调用方法作为复杂均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServiceMetaInfo selectedServiceMetaInfo = balancer.select(requestParams, serviceMetaInfoList);

        // 发送TCP请求
        // 使用重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getTolerantStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();
    }
}
