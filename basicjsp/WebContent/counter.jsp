<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 선언부 -->
<%!
	int cnt;
	int totalLen;
	
	public void init() {
		cnt = 0;
		totalLen = 8;
	}
%>

<!-- 처리부 -->
<%
	cnt++;
	String str = cnt + "";
	int len = str.length();	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
	당신은 
	<%
		for(int i= 0; i < totalLen-len; i ++) {
	%> 
			<img src="/basicjsp/img/0.PNG">	
	<%
		}
		for(int i = 0; i < len; i++) {
	%>
			<img src="/basicjsp/img/<%=str.charAt(i)%>.PNG">
	<%
		}		
	%>
	번째 방문자입니다.</div>
</body>
</html>