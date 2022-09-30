<%@ page import="cn.mr.clock.util.DateTimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: aolixin
  Date: 2022/4/19
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>日报</title>
    <script src="https://ajax.aspnetcdn.com/ajax/jquery/jquery-1.9.0.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function (){
            getDayOption($('#year').val(),$('#month').val());
        })
        //处理请求函数
        function showResult(data) {
            $("#day").html(data);
        }
        //ajax函数
        function getDayOption(year,month) {
            $.ajax({
                url: "<%=request.getContextPath()%>/GetLastDayOfmonth",	//上传URL
                type: "POST", //请求方式
                data: {"year":year,
                    "month":month
                }, //需要上传的数据
                dataType: "text", //设置接受到的响应数据的格式
                success: function (data) {	//请求成功
                    showResult(data);
                },
                error: function () {
                    alert("出错啦...");
                },//表示如果请求响应出现错误，会执行的回调函数
            });
        }


        function getReport(year,month,day){
            $.ajax({
                type : "post",
                async : false,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url : "<%=request.getContextPath()%>/GetDayReport",    //请求发送到UserServlet处
                data : {"year":year,
                        "month":month,
                        "day":day
                        },
                dataType : "text",        //返回数据形式为json
                success : function(result) {
                    $('#area').html(result);
                },
                error : function(errorMsg) {
                    alert("请求数据失败!");
                    myChart.hideLoading();
                }
            });
        }
    </script>
</head>
<style>
    body{text-align:center}
</style>


<body >
    <select name="year" id="year" onclick="getDayOption($('#year').val(),$('#month').val())">>
        <option value="2022">2022</option>
        <option value="2023">2023</option>
        <option value="2024">2024</option>
    </select><label for="year">年</label>

    <select name="month" id="month" onclick="getDayOption($('#year').val(),$('#month').val())">
        <% for (int i = 1;i<=12;i++){
            out.print("<option value=\""+i+"\">"+i+"</option>");
        }%>
    </select><label for="month">月</label>

    <select name="day" id="day">
        <option value="1">1</option>
    </select><label for="day">日</label>
    <div id="submit">
        <button onclick="getReport($('#year').val(),$('#month').val(),$('#day').val())">刷新</button>
    </div>
<% String re=(String)request.getAttribute("dayReport");  %>

        <label style="align-content: center;">
            <textarea cols="60" rows="10" name="event" style="align-content: center" id="area">

                </textarea>
        </label>

</body>
</html>
