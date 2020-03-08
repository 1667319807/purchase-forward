package com.hbpu.service.impl;

import com.hbpu.dao.UserDao;
import com.hbpu.dao.impl.UserDaoImpl;
import com.hbpu.pojo.User;
import com.hbpu.service.UserService;

/**
 * @author qiaolu
 * @time 2020/2/29 10:29
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    @Override
    public boolean checkUser(User user) {

        return userDao.check(user);
    }
}
