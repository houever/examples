package com.juc.controller;

import com.juc.service.ProductService;
import com.juc.service.StudentService;
import com.juc.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@RestController
public class UserController {

    @Resource
    ProductService productService;
    @Resource
    UserService userService;
    @Resource
    StudentService studentService;

    /**
     * 测试慢的方法执行效果
     * @return
     * @throws Exception
     */
    @GetMapping(value = "test")
    public String test() throws Exception {
        long start = System.currentTimeMillis();
        String product = productService.getProduct();
        String userInfo = userService.getUserInfo();
        String studentInfo = studentService.getStudentInfo();
        long end = System.currentTimeMillis();
        System.out.println("调用三个方法执行业务共花费了时间==>"+(end-start));
        return product+userInfo+studentInfo;
    }

    @GetMapping(value = "test2")
    public String test2() throws Exception {
        long start = System.currentTimeMillis();
        Callable<String> c1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return productService.getProduct();
            }
        };
        Callable<String> c2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return userService.getUserInfo();
            }
        };
        Callable<String> c3 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return studentService.getStudentInfo();
            }
        };

        FutureTask<String> f1 = new FutureTask<String>(c1);
        String s1 = f1.get();
        FutureTask<String> f2 = new FutureTask<String>(c1);
        String s2 = f2.get();
        FutureTask<String> f3 = new FutureTask<String>(c1);
        String s3 = f3.get();

        long end = System.currentTimeMillis();
        System.out.println("调用三个方法执行业务共花费了时间==>"+(end-start));
        return s1+s2+s3;
    }
}
