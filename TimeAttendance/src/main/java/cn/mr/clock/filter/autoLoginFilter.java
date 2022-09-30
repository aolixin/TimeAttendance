package cn.mr.clock.filter;

import cn.mr.clock.pojo.User;
import cn.mr.clock.service.HRService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "autoLoginFilter",value = "/*")
public class autoLoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies = req.getCookies();
        String autoLogin = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName()!=null && cookie.getName().equals("autoLogin")) {
                    autoLogin = cookie.getValue();
                    break;
                }
            }
        }
        if (autoLogin != null) {
            String[] parts = autoLogin.split("-");
            if (parts.length == 2 && HRService.userLogin(parts[0],parts[1])) {
                User user = new User(parts[0], parts[1]);
                req.getSession().setAttribute("user",user);
            }else {
                req.getRequestDispatcher(req.getContextPath()+"/login.jsp").forward(req, resp);
            }
        }
        chain.doFilter(req, resp);
    }
}
