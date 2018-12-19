package com.july.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.july.dubbo.api.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @Reference(version = "${user.service.version}")
    private IUserService userService;

    @GetMapping(value = "hello")
    public String hello(String name){
        return userService.sayHi(name);
    }

}

