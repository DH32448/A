<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>show</title>
</head>
<body>
<strong>课程管理</strong>
<div align="center">
    <li><a href="show.do">课程显示</a></li>
    <li><a href="add.do">课程添加</a></li>
</div>
<div>
    <table border="1">
        <tr>
            <th>课程编号</th>
            <th>课程名称</th>
            <th>班级编号</th>
        </tr>
        <c:forEach var="course" items="cs">
            <tr>
                <td>${course.cno}</td>
                <td>${course.cname}</td>
                <td>${course.tid}</td>
                <td><a href="update.do">更改</a></td>
                <td><a href="remove.do">删除</a></td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
