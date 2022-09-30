<%--
  Created by IntelliJ IDEA.
  User: 龙星洛洛
  Date: 2022/4/19
  Time: 下午 07:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>管理员登录</title>
</head>
<body style="display: flex;
    align-items: center;/*水平居中*/;justify-content: center;">
    <form action="<%=request.getContextPath()%>/userLogInServlet" method="post">
        <table style="width:600px ;">
            <tr style="height: 30px;column-span: 2;">
                <td><label for="username">姓名:</label></td>
                <td><input type="text" id="username" name="username"></td>
            </tr>
            <tr style="height: 30px;column-span: 2;">
                <td><label for="password">密码:</label></td>
                <td><input type="password" id="password" name="password"></td>
            </tr>
            <tr style="height: 30px;column-span: 2;">
                <td><input type="submit" value="登录"></td>
                <td><input type="reset" value="重置"></td>
            </tr>
        </table>
    </form>
    <c:if test="${requestScope.errorMsg != null }">
        <div style="color: red">
            <p>错误信息： ${requestScope.errorMsg}</p>
        </div>
    </c:if>
</body>
</html>
