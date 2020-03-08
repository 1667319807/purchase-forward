package com.hbpu.controller;

import com.hbpu.pojo.User;
import com.hbpu.service.UserService;
import com.hbpu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author qiaolu
 * @time 2020/2/29 10:30
 */
@WebServlet("/houtai/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserService service=new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user");
        String pwd = request.getParameter("pwd");
        String savepass = request.getParameter("savepass");
        User user=new User(userName,pwd);
        boolean b = service.checkUser(user);
        if(b){
            Cookie nameCk=new Cookie("userName",userName);
            nameCk.setPath(request.getContextPath());
            System.out.println(request.getContextPath());
            nameCk.setMaxAge(60*60);
            response.addCookie(nameCk);
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            if(savepass!=null){
                Cookie passCk=new Cookie("pwd",pwd);
                passCk.setPath(request.getContextPath());
                passCk.setMaxAge(60*60);
                response.addCookie(passCk);
            }
            response.sendRedirect("/houtai/main.html");
        }else{
            request.setAttribute("info","登陆失败");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            this.doPost(request,response);
    }
}
