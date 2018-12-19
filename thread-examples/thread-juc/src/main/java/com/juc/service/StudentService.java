package com.juc.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
@Service
public class StudentService{


    public String getStudentInfo() throws InterruptedException {
        Thread.sleep(500);
        return "获取学生信息";
    }
}
