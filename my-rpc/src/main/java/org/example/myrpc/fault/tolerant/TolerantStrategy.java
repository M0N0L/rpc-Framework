package org.example.myrpc.fault.tolerant;

import org.example.myrpc.model.RpcResponse;

import java.util.Map;

public interface TolerantStrategy {

    /**
     * 容错机制
     * @param context 上下文数据
     * @param e 异常
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
