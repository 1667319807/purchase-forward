package com.hbpu.service;

import com.hbpu.pojo.User;

/**
 * @author qiaolu
 * @time 2020/2/29 10:28
 */
public interface UserService {
    boolean checkUser(User user);
    int regUser(User user);

    boolean checkUserName(String username);
}
