package org.example.myrpc.fault.tolerant;

import lombok.extern.slf4j.Slf4j;
import org.example.myrpc.model.RpcResponse;

import java.util.Map;

@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常");
        return new RpcResponse();
    }
}
