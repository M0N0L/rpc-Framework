package org.example.myrpc.config;

import lombok.Data;

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


}
