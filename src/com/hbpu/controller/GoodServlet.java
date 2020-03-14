package com.hbpu.controller;

import com.hbpu.pojo.Good;
import com.hbpu.pojo.ShoppingCar;
import com.hbpu.service.GoodService;
import com.hbpu.service.impl.GoodServiceImpl;
import jdk.nashorn.internal.runtime.RewriteException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author qiaolu
 * @time 2020/3/1 12:12
 */
@WebServlet("/qiantai/GoodServlet")
public class GoodServlet extends HttpServlet {
    private GoodService service = new GoodServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("reqType");
        if (method.equals("modCar")) {
            modCar(request, response);
        }
        if (method.equals("delCar")) {
            delCar(request, response);
        }
        if (method.equals("downImg")) {
            downImg(request, response);
        }
        if (method.equals("clearCar")) {
            clearCar(request, response);
        }
        if (method.equals("main")) {
            main(request, response);
        }
        if(method.equals("addToCar")){
            addToCar(request,response);
        }
        if(method.equals("flow")){
            openCar(request,response);
        }

    }

    private void openCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ShoppingCar car = (ShoppingCar) session.getAttribute("car");
        List<Good> goodList = service.findCar(car);
        request.setAttribute("car",goodList);
        request.getRequestDispatcher("flow.jsp").forward(request,response);
    }

    private void addToCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer goodid = Integer.valueOf(request.getParameter("goodid"));
        HttpSession session=request.getSession();
        ShoppingCar car = (ShoppingCar) session.getAttribute("car");
        if(car==null){
            car=new ShoppingCar();
        }
        car.addGood(goodid);

        genericCar(request,response,car,session);
    }
    private void genericCar(HttpServletRequest request, HttpServletResponse response,ShoppingCar car,HttpSession session ) throws ServletException, IOException {
        //添加购物车到session
        session.setAttribute("car",car);
        List<Good> goodList = service.findCar(car);
        request.setAttribute("car",goodList);
        request.getRequestDispatcher("flow.jsp").forward(request,response);
    }


    private void downImg(HttpServletRequest request, HttpServletResponse response) {
        String filename = request.getParameter("filename");
        String path = request.getServletContext().getRealPath("/WEB-INF/upload/" + filename);
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        try {
            fis = new FileInputStream(path);
            sos = response.getOutputStream();
            int len = 0;
            byte b[] = new byte[1024];
            while ((len = fis.read(b)) != -1) {
                sos.write(b, 0, len);
            }
            sos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                sos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String typeName = request.getParameter("typeName");
        List<String> allType = service.getAllType(request, response);
        List<Good> list = null;
        if (allType.size() > 0) {
            if (typeName == null || typeName.isEmpty()) {
                typeName = allType.get(0);
            }
            list = service.queryWithType(typeName);
        }
        HttpSession session=request.getSession();
        ShoppingCar car = (ShoppingCar) session.getAttribute("car");
        int amount=0;
        double price=0;
        if(car!=null){
            amount=car.getAmount();
            price=car.getTotalPrice();
        }
        request.setAttribute("amount", amount);
        request.setAttribute("price", price);
        request.setAttribute("alltype", allType);
        request.setAttribute("list", list);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    private void delCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer goodid = Integer.valueOf(request.getParameter("goodid"));
        HttpSession session=request.getSession();
        ShoppingCar car = (ShoppingCar) session.getAttribute("car");
        car.remove(goodid);
        session.setAttribute("car",car);
        genericCar(request,response,car,session);
    }
    private void clearCar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ShoppingCar car = (ShoppingCar) session.getAttribute("car");
        car.clear();
        genericCar(request,response,car,session);
    }

    private void modCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String goodids[] = request.getParameterValues("goodids");
        String amounts[] = request.getParameterValues("amounts");
        Integer ids[]=new Integer[goodids.length];
        Integer amounts_int[]=new Integer[goodids.length];
        for (int i = 0; i < goodids.length; i++) {
            ids[i]=Integer.valueOf(goodids[i]);
            amounts_int[i]=Integer.valueOf(amounts[i]);
        }
        HttpSession session = request.getSession();
        ShoppingCar car = (ShoppingCar) session.getAttribute("car");
        car.modGood(ids,amounts_int);
        genericCar(request,response,car,session);

    }
}
