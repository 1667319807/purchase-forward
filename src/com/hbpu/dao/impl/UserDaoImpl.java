package com.hbpu.dao.impl;

import com.hbpu.dao.UserDao;
import com.hbpu.dao.basicDao;
import com.hbpu.pojo.User;
import com.hbpu.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qiaolu
 * @time 2020/2/28 20:51
 */
public class UserDaoImpl implements UserDao {
    private basicDao dao = new basicDao();

    @Override
    public boolean check(User user) {
        boolean flag = false;
        Connection connection = Util.getConnection();
        String sql = "select count(*) from user where username=? and userpwd=?";
        PreparedStatement pre = null;
        ResultSet resultSet = null;
        try {
            pre = connection.prepareStatement(sql);
            resultSet = dao.exeQuery(connection, pre, user.getName(), user.getPwd());
            if (resultSet.next()) {
                if(resultSet.getInt(1)!=0){
                    flag = true;
                    return flag;
                }
            } else {
                flag = false;
                return flag;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.close(resultSet, pre, connection);
        }
        return flag;
    }

    @Override
    public int insert(User user) {
        String sql="insert into user(username,userpwd,email,rule,qq) values(?,?,?,1,?)";
        Connection con=null;
        PreparedStatement pst=null;
        int i=0;
        try {
             con = Util.getConnection();
             con.setAutoCommit(false);
             pst = con.prepareStatement(sql);
             i = dao.exeUpdate(con, pst, user.getName(), user.getPwd(), user.getEmail(), user.getQq());
             con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            dao.close(pst,con);
        }
        return i;
    }

    @Override
    public boolean sameName(String username) {
        String sql="select count(*) from user where username=?";
        Connection con = null;
        PreparedStatement pst=null;
        ResultSet res=null;
        boolean flag = false;
        try {
            con = Util.getConnection();
            pst = con.prepareStatement(sql);
            res = dao.exeQuery(con, pst, username);
            if (res.next()&&(res.getInt(1)!=0)) {
                flag = true;
                return flag;
            } else {
                flag = false;
                return flag;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.close(res,pst,con);
        }
        return flag;
    }
}
