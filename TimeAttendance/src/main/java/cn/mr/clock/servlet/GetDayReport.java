package cn.mr.clock.servlet;

import cn.mr.clock.service.HRService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetDayReport", value = "/GetDayReport")
public class GetDayReport extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int year= Integer.parseInt(request.getParameter("year"));
        int month= Integer.parseInt(request.getParameter("month"));
        int day= Integer.parseInt(request.getParameter("day"));
        String dayReport = HRService.getDayReport(year,month,day);

        System.out.println(year+" "+month+" "+day);
        System.out.println(dayReport);

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(dayReport);
    }
}
