package com.me.controller;

import com.me.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private IUserService userService;


    @GetMapping("sayhello")
    public String sayHello(){
        return userService.userSayHello();
    }
}
