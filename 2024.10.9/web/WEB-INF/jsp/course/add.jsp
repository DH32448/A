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
    <title>add</title>
</head>
<body>
<strong>课程管理</strong>

    <a href="show.do">课程显示</a>
    <a href="add.do">课程添加</a>


<div>
    <form method="post" action="/course/add.do">
        <input type="text" name="cno" required="required" placeholder="课程编号">
        <input type="text" name="cname" required="required" placeholder="课程名称">
        <input type="text" name="tid" required="required" placeholder="课程学">
        <input type="submit" value="提交">
    </form>
</div>
</body>
</html>
