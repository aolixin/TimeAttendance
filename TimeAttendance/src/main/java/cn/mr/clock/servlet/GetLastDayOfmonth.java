package cn.mr.clock.servlet;

import cn.mr.clock.util.DateTimeUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetLastDayOfmonth", value = "/GetLastDayOfmonth")
public class GetLastDayOfmonth extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int year = Integer.parseInt(request.getParameter("year"));
        int month = Integer.parseInt(request.getParameter("month"));
        int dayNum = DateTimeUtil.getLastDay(year,month);
        for (int i = 1;i<=dayNum;i++){
            response.getWriter().println("<option value=\""+i+"\">"+i+"</option>");
        }
    }
}
