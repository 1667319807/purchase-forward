package com.hbpu.service.impl;

import com.hbpu.dao.GoodDao;
import com.hbpu.dao.impl.GoodDaoImpl;
import com.hbpu.pojo.Good;
import com.hbpu.service.GoodService;

import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/1 21:12
 */
public class GoodServiceImpl implements GoodService {

    private GoodDao goodDao = new GoodDaoImpl();

    @Override
    public List<Good> queryAll(int pageNow, int pageSize) {
        return goodDao.getAllGood(pageNow, pageSize);
    }

    @Override
    public int queryCount() {
        int count = goodDao.count();
        return count;
    }

    @Override
    public List<Good> queryWithCond(String pid, String gname, String ptype) {
        return goodDao.selectWithCond(pid,gname,ptype);
    }

    @Override
    public int addgood(Good gd) {
        return goodDao.add(gd);
    }

    @Override
    public int updategood(Good good) {
        return goodDao.upteda(good);
    }

    @Override
    public int remove(Integer id) {

        return goodDao.deleteGood(id);
    }
}
