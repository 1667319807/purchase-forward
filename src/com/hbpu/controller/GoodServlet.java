package com.hbpu.controller;

import com.hbpu.pojo.Good;
import com.hbpu.service.GoodService;
import com.hbpu.service.impl.GoodServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/1 12:12
 */
@WebServlet("/houtai/GoodServlet")
public class GoodServlet extends HttpServlet {
    private GoodService service = new GoodServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if (method.equals("query")) {
            query(request, response, -1);
        }
        if (method.equals("queryWithCond")) {
            queryWithCond(request, response);
        }
        if (method.equals("mod")) {
            mod(request, response);
        }
        if (method.equals("remove")) {
            remove(request, response);
        }

    }

    private void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer pageNow = Integer.valueOf(request.getParameter("pageNow"));
        Integer id = Integer.valueOf(request.getParameter("id"));
        int i = service.remove(id);
        int count = service.queryCount();
        if (count % 2 == 0) {
            query(request, response, pageNow - 1);
            //response.sendRedirect("/houtai/GoodServlet?method=query&pageNow="+(pageNow-1));
        } else {
            if (i > 0) {
                try {
                    PrintWriter writer = response.getWriter();
                    writer.print("<script>window.confirm('确定要删除吗？')</script>");
                    query(request, response, pageNow);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                response.setContentType("text/html;charser=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.print("<script>window.alert('修改失败');window.history.back()</script>");
            }
        }
    }

    private void mod(HttpServletRequest request, HttpServletResponse response) {
        Integer pageNow = Integer.valueOf(request.getParameter("pageNow"));
        String pid = request.getParameter("pid");
        String gname = request.getParameter("pname");
        String type = request.getParameter("ptype");
        String price = request.getParameter("pprice");
        Good good = new Good(pid, gname, type, Double.valueOf(price), null);
        int i = service.updategood(good);
        if (i > 0) {
            query(request, response, pageNow);
        }
    }

    private void query(HttpServletRequest request, HttpServletResponse response, int updatePage) {
        Integer pageNow;
        if (updatePage == -1) {
            pageNow = Integer.valueOf(request.getParameter("pageNow"));
            if (pageNow <= 1) {
                pageNow = 1;
            }
        } else {
            pageNow = updatePage;
        }
        List<Good> list = service.queryAll((pageNow - 1) * 2, 2);
        int count = service.queryCount();
        int totalPage = 0;
        if (count % 2 == 0) {
            totalPage = count / 2;
        } else {
            totalPage = count / 2 + 1;
        }
        request.setAttribute("pageNow", pageNow);
        request.setAttribute("goodlist", list);
        request.setAttribute("count", count);
        request.setAttribute("totalPage", totalPage);
        try {
            request.getRequestDispatcher("productListUI.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void queryWithCond(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        String gname = request.getParameter("pname");
        String ptype = request.getParameter("ptype");
        List<Good> list = service.queryWithCond(pid, gname, ptype);
        int count = service.queryCount();
        int totalPage = 0;
        if (count % 2 == 0) {
            totalPage = count / 2;
        } else {
            totalPage = count / 2 + 1;
        }
        request.setAttribute("count", count);
        request.setAttribute("goodlist", list);
        request.setAttribute("totalPage", totalPage);
        try {
            request.getRequestDispatcher("productListUI.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
