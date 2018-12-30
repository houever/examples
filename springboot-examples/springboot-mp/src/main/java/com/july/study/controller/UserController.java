package com.july.study.controller;

import com.july.study.mapper.IUsersMapper;
import com.july.study.model.Users;
import com.july.study.service.IUsersService;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping(value = "/users")
public class UserController {


    @Resource
    private IUsersService usersService;

    /**
     * 添加用户
     * @param users
     * @return
     */
    @PostMapping(value = "add")
    public synchronized String addUsers(Users users){
        //使用多线程的方式添加用户，有效利用cpu的资源进行处理，加速响应时间
        new Thread(new Runnable() {
            @Override
            public void run() {
                Integer insert = usersService.insert(users);
            }
        }).start();

        return null;
    }

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping(value = "/getUsersList")
    public List<Users> getUsersList(){

        //使用lock锁
        Lock lock = new ReentrantLock();
        List<Users> list = usersService.getUsersList();
        lock.unlock();;
        return list;
    }
}
