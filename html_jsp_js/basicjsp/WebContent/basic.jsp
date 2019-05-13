<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 선언부 -->
 <%!
 String name;
 int age;
 
 public void init() {
	 name = "홍길동";
	 age = 25;
 }
 %>
 
<!-- 처리부 -->
 <%
  String color = age >= 18 ? "red" : "blue";
 %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<!-- 처리부를 이용한 출력 -->
		<% out.print(name);%>(<font color= "<% out.print(color); %>"><% out.print(age);%></font>)님 안녕하세요.<br>
		<!-- 출력부를 이용한 출력 -->
		<%= name%>(<font color = "<%= color %>"><%= age %></font>)님 안녕하세요.
	</div>
</body>
</html>