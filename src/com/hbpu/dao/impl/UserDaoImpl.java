package com.hbpu.dao.impl;

import com.hbpu.dao.UserDao;
import com.hbpu.dao.basicDao;
import com.hbpu.pojo.User;
import com.hbpu.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author qiaolu
 * @time 2020/2/28 20:51
 */
public class UserDaoImpl implements UserDao {
    private basicDao dao = new basicDao();

    @Override
    public boolean check(User user) {
        boolean flag = true;
        Connection connection = Util.getConnection();
        String sql = "select count(*) from user where username=? and userpwd=?";
        PreparedStatement pre = null;
        ResultSet resultSet = null;
        try {
            pre = connection.prepareStatement(sql);
            resultSet = dao.exeQuery(connection, pre, user.getName(), user.getPwd());
            if (resultSet != null) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.close(resultSet, pre, connection);
        }
        return flag;
    }
}
