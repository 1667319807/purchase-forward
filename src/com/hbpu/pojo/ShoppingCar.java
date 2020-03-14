package com.hbpu.pojo;

import com.hbpu.service.GoodService;
import com.hbpu.service.impl.GoodServiceImpl;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiaolu
 * @time 2020/3/11 20:13
 */
public class ShoppingCar {
    Map<Integer, Integer> car;

    public Map<Integer, Integer> getCar() {
        return car;
    }

    public void setCar(Map<Integer, Integer> car) {
        this.car = car;
    }

    public void addGood(Integer id) {
        if (car == null) {
            car = new HashMap<>();
        }
        car.put(id,1);
    }

    public void clear(){
        if (car != null) {
            car.clear();
        }
    }
    public void remove(Integer id){
        if (car != null) {
            car.remove(id);
        }
    }
    public void modGood(Integer[] ids,Integer[] nums ){
        for (int i = 0; i < ids.length; i++) {
            car.put(ids[i],nums[i]);
        }
    }
    public int getAmount(){
        int amount=0;
        for(Map.Entry<Integer,Integer> map:car.entrySet()){
            amount+=map.getValue();
        }
        return amount;
    }
    public Double getTotalPrice(){
        GoodService service=new GoodServiceImpl();
        double price=0;
        List<Good> car = service.findCar(this);
        for (Good good : car) {
            price+=good.getNum()*good.getPrice();
        }
        return price;
    }
}
