package com.hbpu.dao;

import com.hbpu.pojo.Good;

import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/1 20:00
 */
public interface GoodDao {
    List<Good> getAllGood(int pageNow, int pageSize);
    int count();
    List<Good> selectWithCond(Object... para);

    int add(Good gd);

    int upteda(Good good);

    int deleteGood(Integer id);
}
