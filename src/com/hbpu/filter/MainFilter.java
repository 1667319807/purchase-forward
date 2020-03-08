package com.hbpu.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author qiaolu
 * @time 2020/3/1 10:25
 */
//@WebFilter("/houtai/main.html")
public class MainFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse responser = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        if(session!=null||session.getAttribute("user")==null){
            responser.sendRedirect("index.jsp");
        }else{
            chain.doFilter(req, resp);

        }

    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
