<%--
  Created by IntelliJ IDEA.
  User: 乔路
  Date: 2020/3/14
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="testsvl" method="post">
    <input type="radio" name="sex" value="F">男
    <input type="radio" name="sex" value="W">女
    <input type="checkbox" name="hobby" value="basket">篮球
    <input type="checkbox" name="hobby" value="foot">足球
    <input type="checkbox" name="hobby" value="swim">游泳
    <br>
    <select name="select" id="">
        <option value="1">10</option>
        <option value="2">20</option>
    </select>

    <input type="submit" value="提交">
</form>
</body>
</html>
