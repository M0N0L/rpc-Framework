package org.example.myrpc.registry;

import org.example.myrpc.config.RegistryConfig;
import org.example.myrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心，所有的服务都会注册在这里
 */
public interface Registry {
    /**
     * 初始化
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现 （获取某服务的所有节点）
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 心跳检测
     */
    void heartBeat();

    /**
     * 消费端监听节点是否变化
     */
    void watch(String serviceNodeKey);
}
