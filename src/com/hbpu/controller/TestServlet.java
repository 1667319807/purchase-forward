package com.hbpu.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qiaolu
 * @time 2020/3/14 17:31
 */
@WebServlet(value = "/testsvl")
public class TestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sex = request.getParameter("sex");
        System.out.println(sex);
        String[] hobbies = request.getParameterValues("hobby");
        for (int i = 0; i < hobbies.length; i++) {
            System.out.println(hobbies[i]);
        }
        String select = request.getParameter("select");
        System.out.println(select);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
