package com.hbpu.dao.impl;

import com.hbpu.dao.GoodDao;
import com.hbpu.dao.basicDao;
import com.hbpu.pojo.Good;
import com.hbpu.pojo.ShoppingCar;
import com.hbpu.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<Good> selectWithType(String type) {
        Connection con = Util.getConnection();
        String sql = "select * from good where type =?";
        ResultSet resultSet =null;
        PreparedStatement pre=null;
        List<Good> list = new ArrayList<>();
        try {
            pre = con.prepareStatement(sql);
            resultSet=dao.exeQuery(con,pre,type);
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

    @Override
    public List<String> selectAllType() {
        List<String> list=new ArrayList<>();
        String sql="select distinct type from good  ";
        Connection con = null;
        PreparedStatement pst=null;
        ResultSet res=null;
        try {
            con = Util.getConnection();
            pst = con.prepareStatement(sql);
            res = dao.exeQuery(con, pst);
            while (res.next()){
                list.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dao.close(res,pst,con);
        }

        return list;
    }

    @Override
    public List<Good> queryCar(ShoppingCar car) {
        List<Good> list = new ArrayList<>();
        Map<Integer, Integer> c = car.getCar();
        StringBuffer ids=new StringBuffer();
        for (Map.Entry<Integer,Integer> entry:c.entrySet()){
            Integer id=entry.getKey();
            ids.append(id.toString()).append(",");
        }
        String idStr=ids.toString();
        if(!idStr.isEmpty()){
            int pos = idStr.lastIndexOf(",");
            idStr=idStr.substring(0,pos);
        }else{
            return list;
        }
        Connection con=Util.getConnection();
        String sql = "select id,gname,price from good where id in ("+idStr+")";
        PreparedStatement pre= null;
        ResultSet resultSet=null;

        try {
            pre = con.prepareStatement(sql);
            resultSet = dao.exeQuery(con,pre);
            while (resultSet.next()) {
                Good good = new Good();
                good.setId(resultSet.getString(1));
                good.setGname(resultSet.getString(2));
                good.setPrice(resultSet.getDouble(3));
                good.setNum(c.get(resultSet.getInt(1)));
                list.add(good);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            dao.close(resultSet,pre,con);
        }
        return list;
    }

}
