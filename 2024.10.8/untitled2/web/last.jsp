<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2024/10/8
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>尾页</title>
</head>
<body>
<div  align="center">
    <c:forEach var="l" items="${t}">
        <li>${l}</li>
    </c:forEach>
</div>
<h1 align="center">尾页</h1>
<a href="first.do">首页</a>
<a href="prev.do">上页</a>
<div>第
    <c:out value="${sessionScope.b}">
    </c:out>/
    <c:out value="${sessionScope.a}">
</c:out>
    页
</div>
<a href="next.do">下页</a>
<a href="last.do">尾页</a>
<div>
    <form method="post" action="changed.do">
        <input name="a" placeholder="页数" type="text">
        <input name="b" placeholder="行数" type="text">
        <button>确定</button>
    </form>
</div>
</body>
</html>
