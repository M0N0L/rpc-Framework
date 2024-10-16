package org.example.myrpc;

import lombok.extern.slf4j.Slf4j;
import org.example.myrpc.config.RpcConfig;
import org.example.myrpc.constant.RpcConstant;
import org.example.myrpc.utils.ConfigUtils;

/**
 * RPC框架应用
 * 存放了项目全局用到的变量，双检锁单例模式
 */
@Slf4j
public class RpcApplication {
    // volatile 关键字确保多线程下的可见性
    private static volatile RpcConfig rpcConfig;

    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
    }

    /**
     * 初始化
     * 支持在获取配置时才调用 init 方法实现懒加载
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try{
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFUALT_CONFIG_PREFIX);
        } catch (Exception e) {
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     *
     */
    public static RpcConfig getRpcConfig() {
        if(rpcConfig == null) {
            synchronized ( (RpcApplication.class)) {
                if(rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
