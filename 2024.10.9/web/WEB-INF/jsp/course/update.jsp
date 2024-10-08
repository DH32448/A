<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2024/10/8
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课程练习</title>
</head>
<body>
<strong>课程管理</strong>
<div align="center">
    <li><a href="show.do">课程显示</a></li>
    <li><a href="add.do">课程添加</a></li>
</div>
<div>
    <form method="post" action="/course/update.do">
        <input type="text" name="" required="required" placeholder="课程编号">
        <input type="text" name="" required="required" placeholder="课程名称">
        <input type="text" name="" required="required" placeholder="课程学分">
        <input type="submit" value="更新">
    </form>
</div>
</body>
</html>
