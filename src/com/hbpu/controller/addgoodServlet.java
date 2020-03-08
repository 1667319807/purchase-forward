package com.hbpu.controller;

import com.hbpu.pojo.Good;
import com.hbpu.service.GoodService;
import com.hbpu.service.impl.GoodServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author qiaolu
 * @time 2020/3/5 12:09
 */
@WebServlet("/houtai/addgoodServlet")
public class addgoodServlet extends HttpServlet {
    GoodService service = new GoodServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        DiskFileItemFactory dif = new DiskFileItemFactory();
        //获取文件需要上传的路径
        String path = getServletContext().getRealPath("/WEB-INF/upload");
        //指定临时文件目录
        dif.setRepository(new File(path));
        //设置内存缓冲区大小
        dif.setSizeThreshold(1024 * 1024);
        //
        ServletFileUpload upload = new ServletFileUpload(dif);
        /*ProgressListener pl = new ProgressListener() {
            @Override
            public void update(long pByteRead, long pContentLength, int pItems) {
                System.out.println("到现在为止" + pByteRead + "字节已上传，总大小为：" + pContentLength);
            }
        };
        upload.setProgressListener(pl);*/
        List<FileItem> list;
        try {
            list = upload.parseRequest(request);
            Good gd = new Good();
            for (FileItem item : list) {
                String name = item.getFieldName();
                if (item.isFormField()) {
                    String value = new String(item.getString().getBytes("iso-8859-1"),"UTF-8");
                    if (name.equals("pname")) {
                        gd.setGname(value);
                    } else if (name.equals("ptype")) {
                        gd.setType(value);
                    } else if (name.equals("pprice")) {
                        gd.setPrice(Double.valueOf(value));
                    } else {

                    }

                } else {
                    String value = item.getName();
                    int start = value.lastIndexOf("\\");
                    String filename = value.substring(start + 1);
                    //把文件数据写到Upload目录下
                    gd.setPic(filename);
                    item.write(new File(path, filename));
                }
            }
            int i = service.addgood(gd);
            if (i > 0) {
                response.sendRedirect("/houtai/GoodServlet?method=query&pageNow=1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
