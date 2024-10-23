package org.example.myrpc.fault.retry;

import org.example.myrpc.model.RpcResponse;

import java.util.concurrent.Callable;

public interface RetryStrategy {
    /**
     * 重试方法
     * @param callable
     * @return
     * @throws Exception
     */
    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;
}
