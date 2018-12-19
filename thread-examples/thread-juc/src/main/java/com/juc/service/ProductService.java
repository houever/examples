package com.juc.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
@Service
public class ProductService{

    public String getProduct() throws InterruptedException {
        Thread.sleep(500);
        return "获取商品列表";
    }
}