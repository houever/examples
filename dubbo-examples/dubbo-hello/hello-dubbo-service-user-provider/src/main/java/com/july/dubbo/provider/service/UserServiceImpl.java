package com.july.dubbo.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.july.dubbo.api.IUserService;
import org.springframework.stereotype.Component;

@Service(version = "${user.service.version}")
public class UserServiceImpl implements IUserService {

    @Override
    public String sayHi(String name) {
        return "Hello "+name;
    }
}
