package cn.mr.clock.servlet;

import cn.mr.clock.service.HRService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 删除学生Servlet
 * @author 龙星洛洛
 */
@WebServlet(name = "deleteStudentServlet", value = "/deleteStudentServlet")
public class deleteStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * 重写Post函数
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String studentId = request.getParameter("studentId");//获取学号
        HRService.deleteStu(studentId.toString());//删除学生
        response.getWriter().println("删除学生studentId");
    }
}
