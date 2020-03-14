package com.hbpu.service.impl;

import com.hbpu.dao.UserDao;
import com.hbpu.dao.impl.UserDaoImpl;
import com.hbpu.pojo.User;
import com.hbpu.service.UserService;
import com.hbpu.util.Tools;

/**
 * @author qiaolu
 * @time 2020/2/29 10:29
 */
public class UserServiceImpl implements UserService  {
    UserDao userDao = new UserDaoImpl();
    @Override
    public boolean checkUser(User user) {
//      user.setPwd(Tools.md5(user.getPwd()));//登录控制器已经加密过
        return userDao.check(user);
    }

    @Override
    public int regUser(User user) {
        user.setPwd(Tools.md5(user.getPwd()));
        return userDao.insert(user);
    }

    @Override
    public boolean checkUserName(String username) {
        return userDao.sameName(username);
    }
}
