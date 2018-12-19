package com.juc.service;

import org.springframework.stereotype.Service;

import javax.security.auth.callback.Callback;
import java.util.concurrent.Callable;


@Service
public class UserService{

    public String getUserInfo() throws InterruptedException {
        Thread.sleep(500);
        return "获取用户信息";
    }
}
