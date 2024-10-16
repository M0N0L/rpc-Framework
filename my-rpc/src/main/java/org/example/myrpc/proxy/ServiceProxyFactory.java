package org.example.myrpc.proxy;

import org.example.myrpc.RpcApplication;

import java.lang.reflect.Proxy;
import java.security.Provider;

public class ServiceProxyFactory {
    public static <T> T getProxy(Class<T> serviceClass) {
        if(RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }

        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

    public static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new MockServiceProxy()
        );
    }
}
