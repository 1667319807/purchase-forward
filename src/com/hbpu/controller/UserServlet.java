package com.hbpu.controller;

import com.hbpu.pojo.User;
import com.hbpu.service.UserService;
import com.hbpu.service.impl.UserServiceImpl;
import com.hbpu.util.Tools;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet(value = "/qiantai/UserServlet")
public class UserServlet extends HttpServlet {
    private static String codeChars = "1234567890abcdefghijklmnopqrstuvwxyz";
    UserService service = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("reqType");
        if (method.equals("reg")) {
            reg(request, response);
        }
        if (method.equals("checkUserName")) {
            checkUserName(request, response);
        }if(method.equals("login")){
            login(request,response);

        }        if (method.equals("validate")) {
            getValidate(request, response);
        }
    }
    private static Color getRandomColor(int minColor, int maxColor) {
        Random random = new Random();
        if (minColor > 255) {
            minColor = 255;
        }
        if (maxColor > 255) {
            maxColor = 255;
        }
        int red = minColor + random.nextInt(maxColor - minColor);
        int green = minColor + random.nextInt(maxColor - minColor);
        int blue = minColor + random.nextInt(maxColor - minColor);
        return new Color(red, green, blue);
    }
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String pwd = request.getParameter("password");
        String validatecode = request.getParameter("validatecode");
        HttpSession session = request.getSession();
        String validation_code = (String) session.getAttribute("validation_code");
        User user = new User(userName, Tools.md5(pwd));
        boolean b = service.checkUser(user);
        if (validatecode == "" || !validatecode.equalsIgnoreCase(validation_code)) {
            request.setAttribute("errvali", "验证码错误");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            if (b) {
                session.setAttribute("user", user);
                response.sendRedirect("/qiantai/GoodServlet?reqType=main");
            } else {
                request.setAttribute("info", "登陆失败");
                response.getWriter().print("<script>window.history.back()</script>");
                response.getWriter().flush();
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getValidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得验证码集合的长度
        int charsLength = codeChars.length();
        //下面3条是关闭客户端浏览器的缓冲区
        response.setHeader("ragma", "No-cache");
        response.setHeader("Cach-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //设置图形验证码的长宽
        int width = 90, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();//获得输出文字的graphics对象
        Random random = new Random();
        g.setColor(getRandomColor(180, 250));//背景颜色
        g.fillRect(0, 0, width, height);
        //设置初始字体
        g.setFont(new Font("Times New Roman", Font.ITALIC, height));
        g.setColor(getRandomColor(120, 180));//字体颜色
        StringBuilder validationCode = new StringBuilder();
        //验证码的随机字体
        String[] fontNames = {"Times New Roman", "Book antiqua", "Arial"};
        //随机生成3-5个验证码
        for (int i = 0; i < 3 + random.nextInt(3); i++) {
            //随机设置当前验证码的字符的字体
            g.setFont(new Font(fontNames[random.nextInt(3)], Font.ITALIC, height));
            //随机获得当前验证码的字符
            char codeChar = codeChars.charAt(random.nextInt(charsLength));
            validationCode.append(codeChar);
            //随机设置当前验证码字符的颜色
            g.setColor(getRandomColor(10, 100));
            //在图形上输出验证码字符，x y随机生成
            g.drawString(String.valueOf(codeChar), 16 * i + random.nextInt(7), height - random.nextInt(6));
        }
        //获得session对象
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(5 * 60);
        //将验证码保存在session对象中，key为validation_code
        session.setAttribute("validation_code", validationCode.toString());
        g.dispose();
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "JPEG", os);//以JPEG格式向客户端发送图形验证码
    }


    private void checkUserName(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        boolean b = service.checkUserName(username);
        if (b) {
            try {
                PrintWriter writer = response.getWriter();
                writer.print(b);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect("register.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                response.sendRedirect("login.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                request.setAttribute("info", "注册失败");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


