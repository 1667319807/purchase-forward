package com.hbpu.controller;

import com.hbpu.pojo.User;
import com.hbpu.service.UserService;
import com.hbpu.service.impl.UserServiceImpl;
import jdk.nashorn.internal.runtime.RewriteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author qiaolu
 * @time 2020/3/8 20:21
 */
@WebServlet("qiantai/RegServlet")
public class RegServlet extends HttpServlet {
    UserService service = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if (method.equals("reg")) {
            reg(request, response);
        }
    }

    private void reg(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String qqcode = request.getParameter("qqcode");
        User user = new User(null, username, password, email, null, qqcode);
        int i = service.regUser(user);
        if (i > 0) {
            try {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("login.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                request.setAttribute("info","注册失败");
                request.getRequestDispatcher("register.jsp").forward(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
