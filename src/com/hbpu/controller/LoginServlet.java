package com.hbpu.controller;

import com.hbpu.pojo.User;
import com.hbpu.service.UserService;
import com.hbpu.service.impl.UserServiceImpl;
import com.hbpu.util.Tools;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author qiaolu
 * @time 2020/2/29 10:30
 */
@WebServlet("/qiantai/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserService service = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String pwd = request.getParameter("password");
        String validatecode = request.getParameter("validatecode");
        HttpSession session = request.getSession();
        String validation_code = (String) session.getAttribute("validation_code");
        User user = new User(userName, Tools.md5(pwd));
        boolean b = service.checkUser(user);
        if (validatecode == "" ||!validatecode.equalsIgnoreCase(validation_code)) {
            request.setAttribute("errvali", "验证码错误");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        } else {
            if (b) {
                response.sendRedirect("/qiantai/index.jsp");
            } else {
                request.setAttribute("info", "登陆失败");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
