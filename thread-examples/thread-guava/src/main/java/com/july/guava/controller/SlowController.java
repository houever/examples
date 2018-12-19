package com.july.guava.controller;

import com.july.guava.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SlowController {

    @Resource
    private UserService userService;
    @GetMapping(value = "test2")
    public String test2(){
        long starttime = System.currentTimeMillis();
        userService.addressService();
        userService.stockService();
        userService.itemService();
        userService.userService();
        long endtime = System.currentTimeMillis();
        return (endtime-starttime)+"";
    }
}
