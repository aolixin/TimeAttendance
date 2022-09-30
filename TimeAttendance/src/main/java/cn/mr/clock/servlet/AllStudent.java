package cn.mr.clock.servlet;

import cn.mr.clock.dao.DAO;
import cn.mr.clock.dao.DAOFactory;
import cn.mr.clock.pojo.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet(name = "AllStudent", value = "/AllStudent")
public class AllStudent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO dao=DAOFactory.getDAO();		//DAO接口

        String[] weekDayStr = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};

        Set<Student> stus =dao.getALLStu();
        JSONArray jsonarray = new JSONArray();
        JSONObject jsonobj = new JSONObject();

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        for (Student stu : stus) {
            jsonobj.put("StuId",stu.getStudentId());
            jsonobj.put("StuName",stu.getName());
            jsonobj.put("StartTime",stu.getWorkTime().getStart());
            jsonobj.put("EndTime",stu.getWorkTime().getEnd());
            jsonobj.put("WeekDay", weekDayStr[stu.getWorkTime().getWeekDay()-1]);
            jsonarray.add(jsonobj);
        }


        PrintWriter out = response.getWriter();
        out.print(jsonarray);
    }
}
