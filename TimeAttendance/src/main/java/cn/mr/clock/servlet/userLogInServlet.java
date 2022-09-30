package cn.mr.clock.servlet;

import cn.mr.clock.pojo.User;
import cn.mr.clock.service.HRService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "userLogInServlet", value = "/userLogInServlet")
public class userLogInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        if (HRService.userLogin(username,password)){
            System.out.println(username + "-" + password);
            User user = new User(username,password);
            request.getSession().setAttribute("user",user);

            System.out.println(username+"-"+password);

            request.getSession().setAttribute("username",username);

            Cookie cookie = new Cookie("autoLogin",username+"-"+password);
            cookie.setMaxAge(30*24*60*60);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);

            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else {
            request.setAttribute("errorMsg","用户名或密码错误");
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }
}
