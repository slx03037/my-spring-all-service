<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
</head>
<body>
<h1>Hello World!</h1>
<p>This is the homepage!</p>
<form action="shiro/login" method="POST">
    username: <input type="text" name="username"/>
    <br><br>
    password: <input type="password" name="password"/>
    <br><br>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>