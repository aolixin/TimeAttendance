<%--
  Created by IntelliJ IDEA.
  User: 龙星洛洛
  Date: 2022/4/20
  Time: 上午 01:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="cn.mr.clock.pojo.Student" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="cn.mr.clock.session.Session" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>学生管理系统</title>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://ajax.aspnetcdn.com/ajax/jquery/jquery-1.9.0.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script>
        <!-- 初始化 -->
        $(document).ready(function (){
            getStudent();
        })
        /*删除学生函数*/
        function deleteStudent(studentId) {
            //询问是否确认删除
            let flag = confirm("确认删除"+studentId+"吗？");
            if(!flag){
                return false;
            }
            $.ajax({
                url:"<%=request.getContextPath()%>/deleteStudentServlet",
                type:"post",
                data:{"studentId":studentId},
                dataType:"text",
                success:function (data) {
                getStudent();
                },
                error:function (){
                    alert("删除学生出错...");
                },
            });
        }
        <!-- 显示学生信息 -->
        function show(name,studentId,start,end,weekday) {
            let tr = $("<tr name='student'></tr>");
            tr.append("<td>"+name+"</td>");
            tr.append("<td >"+studentId+"</td>");
            tr.append("<td>"+start+"</td>");
            tr.append("<td>"+end+"</td>");
            tr.append("<td>"+weekday+"</td>");
             // 删除按钮
            tr.append("<td><button onclick=\"deleteStudent('"+studentId+"' )\">删除</button></td>");
            tr.appendTo("#StuTable");
        }
        <!-- 获取全部学生 -->
        function getStudent() {
            $.ajax({
                url:"<%=request.getContextPath()%>/AllStudent",
                type:"post",
                data:{},
                dataType:"json",
                success:function (students) {
                    //初始化StuTable
                    $('#StuTable').html("<tr> <td>学生名称</td> <td>学生学号</td> <td>打卡开始时间</td> <td>打卡结束时间</td> <td>星期几</td> </tr>");
                    for (let i = 0; i<students.length; i++){
                        show(students[i].StuName,students[i].StuId,students[i].StartTime,students[i].EndTime,students[i].WeekDay);
                    }
                },
                error:function (){
                    alert("获取学生出错...");
                },
            });
        }
    </script>
    <style>
        <!-- table格式 -->
        #StuTable{
            width: 400px;
            margin: 0 auto;
            border: 1px solid #000000;
            border-collapse: collapse;
        }
        #StuTable tr,th,td{
            border: 1px solid #000000;
        }
    </style>
</head>
<body>
<table id="StuTable">

</table>
</body>
</html>
