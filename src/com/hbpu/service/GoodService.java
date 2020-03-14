package com.hbpu.service;

import com.hbpu.pojo.Good;
import com.hbpu.pojo.ShoppingCar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/1 21:11
 */
public interface GoodService {
    List<Good> queryAll(int pageNow, int pageSize);
    int queryCount();

    List<Good> queryWithType(String type);

    int addgood(Good gd);

    int updategood(Good good);

    int remove(Integer id);

    List<String> getAllType(HttpServletRequest request, HttpServletResponse response);
    List<Good> findCar(ShoppingCar car);
}
