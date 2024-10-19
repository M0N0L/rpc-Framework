package org.example.myrpc.config;

import lombok.Data;
import org.example.myrpc.serializer.SerializerKeys;


/**
 * RPC框架配置信息
 */
@Data
public class RpcConfig {
    /**
     * 框架名
     */
    private String name = "my-rpc";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口
     */
    private int serverPort = 8080;

    /**
     * 支持mock
     */
    private boolean mock = false;

    /**
     * 指定序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
}
