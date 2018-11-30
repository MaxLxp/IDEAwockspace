<%--
  Created by IntelliJ IDEA.
  User: liuxingpeng
  Date: 2018/11/17
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body{
            position: relative;
        }
        from{
            top:50%;
            left:50%;
            transform: translate(-50%,-50%);
        }
    </style>
    <script>
    </script>
</head>
<body>
    <form id="login" action="/login" method="post">
        <input id="username" name="username" type="text"/><br/>
        <input id="password" name="password" type="password"/><br/>
        <input type="submit" value="登录">
    </form>
</body>
</html>
