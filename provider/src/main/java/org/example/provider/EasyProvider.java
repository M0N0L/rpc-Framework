package org.example.provider;

import org.example.common.service.UserService;
import org.example.myrpc.bootstrap.ProviderBootStrap;
import org.example.myrpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

public class EasyProvider {
    public static void main(String[] args) {
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);

        // 服务提供者初始化
        ProviderBootStrap.init(serviceRegisterInfoList);
    }
}
