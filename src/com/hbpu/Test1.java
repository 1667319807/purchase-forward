package com.hbpu;

import com.hbpu.dao.GoodDao;
import com.hbpu.dao.impl.GoodDaoImpl;
import com.hbpu.pojo.Good;

import java.util.List;

/**
 * @author qiaolu
 * @time 2020/2/29 9:46
 */
public class Test1 {

    public static void main(String[] args) {
        GoodDao goodDao=new GoodDaoImpl();
        List<Good> list = goodDao.getAllGood(1, 2);
        System.out.println("list.toArray() = " + list.size());
    }
}
