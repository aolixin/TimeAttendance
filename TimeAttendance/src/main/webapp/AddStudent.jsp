<%--
  Created by IntelliJ IDEA.
  User: aolixin
  Date: 2022/4/22
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        form {
            position: absolute;
            top: 120px;
            left: 30%;
        }

        input[type=text], select {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type=submit] {
            width: 50%;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type=submit]:hover {
            background-color: #45a049;
        }

        div {
            border-radius: 5px;
            background-color: #f2f2f2;
            padding: 20px;
        }
    </style>
</head>

<body style=" display: flex;
    align-items: center;/*水平居中*/">

<!--提交信息表单-->
<form action="<%=request.getContextPath()%>/AddStudent" method="post" enctype="multipart/form-data">
    <input name="Name" type="text" placeholder="姓名"><br>
    <input name="StuId" type="text" placeholder="学号"><br>
    工作时间 <br>
    <select name="WorkTime">
        <option value="9:00:00-9:35:00">第一大节</option>
        <option value="10:00:00-10:45:00">第二大节</option>
        <option value="2:00:00-2:45:00">第三大节</option>
        <option value="3:40:00-4:30:00">第四大节</option>
    </select>
    <br>
    工作日<br>
    <select name="WeekDay">
        <option value="1">星期一</option>
        <option value="2">星期二</option>
        <option value="3">星期三</option>
        <option value="4">星期四</option>
        <option value="5">星期五</option>
    </select>
    <br>
    <!--选择图片文件提交-->
    本地图片<br><input type="file" name="image" id="pic"><br>

    <!--选择拍照提交-->
    <!--<input type="button" value="上传" onclick="uopload">-->

    <!--提交按钮-->
    <input type="submit" value="上传" id="in"><br>

    <!--判断是否成功添加-->
    <%if(request.getAttribute("AddedSuccessfully")!=null){
        out.print(request.getAttribute("AddedSuccessfully"));
    }%>

</form>
</body>

</html>
