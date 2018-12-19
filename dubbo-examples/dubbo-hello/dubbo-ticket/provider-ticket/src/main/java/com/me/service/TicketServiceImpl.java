package com.me.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * 加入到容器component
 * dubbo的service
 */
@Component
@Service
public class TicketServiceImpl implements ITicketService {
    @Override
    public String sayHello() {
        return "hello";
    }
}
