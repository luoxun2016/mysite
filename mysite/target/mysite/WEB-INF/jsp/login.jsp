<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<%=request.getContextPath() %>
	<form action="${rc.contextPath}/dologin" method="post">
		用户名<input type="text" name="username" value="${user.username}" />
		<form:errors path="user.username" />
		密码：<input type="text" name="password" value="${user.password}"/>
		<form:errors path="user.password" />
		<img src="<%=request.getContextPath() %>/verifycode"> <input type="submit" value="提交">
		<p>${message}</p>
	</form>
</body>
</html>