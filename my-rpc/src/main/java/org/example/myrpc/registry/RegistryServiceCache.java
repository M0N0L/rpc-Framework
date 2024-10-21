package org.example.myrpc.registry;

import lombok.extern.slf4j.Slf4j;
import org.example.myrpc.model.ServiceMetaInfo;

import java.util.List;


public class RegistryServiceCache {
    /**
     * 缓存服务
     */
    List<ServiceMetaInfo> serviceCache = null;


    void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    List<ServiceMetaInfo> readCache() {

        return this.serviceCache;
    }

    void clearCache() {
        this.serviceCache = null;
    }
}
