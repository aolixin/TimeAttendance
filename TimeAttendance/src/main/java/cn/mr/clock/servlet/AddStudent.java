package cn.mr.clock.servlet;

import cn.mr.clock.pojo.Student;
import cn.mr.clock.pojo.WorkTime;
import cn.mr.clock.service.HRService;
import cn.mr.clock.service.ImageService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static cn.mr.clock.service.ImageService.saveFaceImage;

@WebServlet(name = "AddStudent", value = "/AddStudent")
public class AddStudent extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 开始解析请求信息
        List<FileItem> items = null;
        //加载请求信息
        try {
                items = upload.parseRequest(request);
        }
        catch (FileUploadException e) {
            e.printStackTrace();
        }

        if(items==null){//判断是否有数据
            System.err.println("数据为空");
            return;
        }
        System.out.println(items.size());

        String StuId = null;
        String Name = null;
        String WorkTime = null;
        int WeekDay = 0;
        String Start = null;
        String End = null;

        for (FileItem item:items){
            // 信息为普通的格式
            if (item.isFormField()) {
                String ItemName = item.getFieldName();
                String value = new String(item.getString("UTF-8"));

                if(ItemName.equals("StuId")){
                    StuId = value;
                }
                if(ItemName.equals("Name")){
                    Name= value;
                }
                if(ItemName.equals("WorkTime")){
                    WorkTime = value;
                }
                if(ItemName.equals("WeekDay")){

                   WeekDay = Integer.parseInt(value);
                }

            } else {
                if (item == null) {
                    System.err.println("空照片");
                }
                String basePath = ImageService.Path;
                File file = new File(basePath, StuId+".png");

                try {
                    item.write(file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
        String s[] = WorkTime.split("-");
        Start = s[0];
        End = s[1];
        WorkTime time = new WorkTime(Start,End,WeekDay);
        HRService.addStudent(Name,StuId,time);//添加学生
        request.setAttribute("AddedSuccessfully", Name+"添加成功!");
        getServletContext().getRequestDispatcher("/AddStudent.jsp").forward(request, response);

        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }
}
