package com.hbpu.dao.impl;

import com.hbpu.dao.GoodDao;
import com.hbpu.dao.basicDao;
import com.hbpu.pojo.Good;
import com.hbpu.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/1 20:01
 */
public class GoodDaoImpl implements GoodDao {
    private basicDao dao=new basicDao();
    @Override
    public List<Good> getAllGood(int pageNow, int pageSize) {
        Connection con=Util.getConnection();
        String sql = "select id,gname,type,price,pic from good limit ?,?";
        PreparedStatement pre= null;
        ResultSet resultSet=null;
        List<Good> list = new ArrayList<>();
        try {
            pre = con.prepareStatement(sql);
            resultSet = dao.exeQuery(con,pre,pageNow,pageSize);
            while (resultSet.next()) {
                Good good = new Good();
                good.setId(resultSet.getString(1));
                good.setGname(resultSet.getString(2));
                good.setType(resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                good.setPic(resultSet.getString(5));
                list.add(good);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dao.close(resultSet,pre,con);
        }
        return list;
    }

    @Override
    public List<Good> selectWithCond(Object... para) {
        Connection con = Util.getConnection();
        String sql = "select id,gname,type,price,pic from good";
        String Cond = "";
        ResultSet resultSet =null;
        PreparedStatement pre=null;
        if (((String) para[0]).trim() != "") {
            Cond = " where id like '%" + ((String) para[0]).trim() + "%'";
        }else{
            Cond=" where id like '%%'";
        }
        if (((String) para[1]).trim() != "") {
            Cond += " and gname like '%" + ((String) para[1]).trim() + "%'";
        } else {
            Cond += " and gname like '%%'";
        }if(((String) para[2]).trim() != ""){
            Cond += " and type like '%" + ((String) para[2]).trim() + "%'";
        }else{
            Cond += " and type like '%%'";
        }
        sql += Cond;
        List<Good> list = new ArrayList<>();
        try {
            pre = con.prepareStatement(sql);
            resultSet=dao.exeQuery(con,pre);
            while (resultSet.next()) {
                Good good = new Good();
                good.setId(resultSet.getString(1));
                good.setGname(resultSet.getString(2));
                good.setType(resultSet.getString(3));
                good.setPrice(resultSet.getDouble(4));
                list.add(good);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          dao.close(resultSet,pre,con);
        }
        return list;
    }

    @Override
    public int count() {
        Connection con = Util.getConnection();
        String sql = "select count(*) from good";
        PreparedStatement pre=null;
        ResultSet res=null;
        int count = 0;
        try {
            pre = con.prepareStatement(sql);
            res = dao.exeQuery(con, pre);
            if (res.next()) {
                count = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.close(res,pre,con);
        }
        return count;
    }

    @Override
    public int add(Good gd) {
        Connection con = Util.getConnection();
        String sql="insert into good (gname,type,price,pic) values (?,?,?,? )";
        PreparedStatement pre=null;
        int i =0;
        try {
            pre = con.prepareStatement(sql);
            i = dao.exeUpdate(con, pre, gd.getGname(), gd.getType(), gd.getPrice(), gd.getPic());
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dao.close(pre,con);
        }
        return i;
    }

    @Override
    public int upteda(Good good) {
        Connection con = Util.getConnection();
        PreparedStatement pre=null;
        int i =0;
        String sql="update good set gname=?,type=?,price=? where id=?";
        try {
            pre=con.prepareStatement(sql);
            i = dao.exeUpdate(con, pre, good.getGname(), good.getType(), good.getPrice(), good.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int deleteGood(Integer id) {
        Connection con = Util.getConnection();
        PreparedStatement pre=null;
        int i =0;
        String sql="delete from good where id=?";
        try {
            pre = con.prepareStatement(sql);
             i = dao.exeUpdate(con, pre, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
