package com.july.study.service;

import com.july.study.model.Users;

import java.util.List;

public interface IUsersService {

    Integer insert(Users users);

    List<Users> getUsersList();
}
