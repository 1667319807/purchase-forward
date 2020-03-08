package com.hbpu.service;

import com.hbpu.pojo.Good;

import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/1 21:11
 */
public interface GoodService {
    List<Good> queryAll(int pageNow, int pageSize);
    int queryCount();

    List<Good> queryWithCond(String pid, String gname, String ptype);

    int addgood(Good gd);

    int updategood(Good good);

    int remove(Integer id);
}
