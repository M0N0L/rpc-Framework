package org.example.myrpc.loadbalancer;

import org.example.myrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性哈希负载均衡器
 */
public class ConsistentHashLoadBalancer implements LoadBalancer{
    /**
     * 哈希环
     */
    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();

    private static final int VIRTUAL_NODE_NUM = 100;


    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if(serviceMetaInfoList.isEmpty()) {
            return null;
        }

        for(ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for(int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(serviceMetaInfo.getServiceAddress() + '#' + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }

        //获取调用节点的哈希值
        int hash = getHash(requestParams);

        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if(entry == null) {
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();

    }

    /**
     * HASH 算法
     * @param key
     * @return
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
