<%@ page import="cn.mr.clock.pojo.Student" %>
<%@ page import="cn.mr.clock.pojo.User" %><%--
  Created by IntelliJ IDEA.
  User: 龙星洛洛
  Date: 2022/4/19
  Time: 下午 09:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>
</head>
<body style="display: flex;flex-direction: column;
      justify-content: center;align-items: center; margin:0 auto;">
    <%  User user = null;
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }else{
        user =(User) request.getSession().getAttribute("user");
    }%>
    <div style="box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);display: flex;flex-direction: column;
			justify-content: center;align-items: center;width:200px;height:300px;position: center;">
        <p> hello! <% if (user !=null){out.print(user.getUsername());}%></p>
        <p><a href="<%=request.getContextPath() %>/studentManageSystem.jsp"> 学生管理系统 </a></p>
        <p><a href="<%=request.getContextPath() %>/DayReport.jsp"> 查看日报 </a></p>
        <p><a href="<%=request.getContextPath() %>/AddStudent.jsp"> 添加新学生 </a></p>
        <p><a href="<%=request.getContextPath() %>/logoutServlet"> 注销 </a></p>
    </div>
</body>
</html>
