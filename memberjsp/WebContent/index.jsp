<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div align="center">
	<h3>JSP을 이용한 회원가입 &amp; 로그인</h3>
	<a href="<%=root %>/user/member.jsp">회원가입</a><br>
	<a href="<%=root %>/user/login.jsp">로그인</a><br>
</div>
</body>
</html>