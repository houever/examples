package com.july.guava.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    /**
     * 商品服务
     * @return
     */
    public long itemService(){
        System.out.println("商品服务开始执行");
        try {
            Thread.sleep(3000);
            throw new RuntimeException("服务异常了。。。。。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("商品服务执行结束");
        return 5;
    }
    /**
     * 地址服务
     * @return
     */
    public long addressService(){
        System.out.println("地址服务开始执行");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("地址服务执行结束");
        return 5;
    }
    /**
     * 库存服务
     * @return
     */
    public long stockService(){
        System.out.println("库存服务开始执行");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("库存服务执行结束");
        return 5;
    }
    /**
     * 用户服务
     * @return
     */
    public long userService(){
        System.out.println("用户服务开始执行");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("用户服务执行结束");
        return 5;
    }

}
