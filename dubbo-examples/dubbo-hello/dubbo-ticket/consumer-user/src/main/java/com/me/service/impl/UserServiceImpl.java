package com.me.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.me.service.ITicketService;
import com.me.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Reference
    private ITicketService ticketService;


    @Override
    public String userSayHello() {
        String s = ticketService.sayHello();
        return s;
    }
}
