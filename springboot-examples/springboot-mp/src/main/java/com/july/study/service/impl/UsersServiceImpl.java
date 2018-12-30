package com.july.study.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.july.study.mapper.IUsersMapper;
import com.july.study.model.Users;
import com.july.study.service.IUsersService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UsersServiceImpl implements IUsersService {

    @Resource
    private IUsersMapper usersMapper;
    @Override
    public Integer insert(Users users) {
        return usersMapper.insert(users);
    }

    @Override
    public List<Users> getUsersList() {

        EntityWrapper<Users> wrapper = new EntityWrapper<>();
        return null;
    }
}
