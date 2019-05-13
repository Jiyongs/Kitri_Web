<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 선언부 -->
<%!
	int dan = 1;
	int i = 1;
	
	String color="";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<!-- out.println만 -->
		<h3>*** 구구단 ***</h3>
		<table width="800" height="700" border="1px">
		<%
			for(int i = 2; i < 10; i++) {
				out.println("			<tr align=\"center\">");
				for(int j = 1; j < 10; j++) {
					String color = i % 2 == 0 ? "tomato" : "lightgray";
					out.println("				<td bgcolor=\"" + color + "\">" + i + " * " + j + " = " + i*j + "</td>");	
				}
				out.println("			</tr>");
			}
		%>
		</table>
		
		<br>
		<hr>
		
		<!-- 출력태그만 -->
		<h3>*** 구구단 ***</h3>
		<table width="800" height="700" border="1px">
		<% for(dan = 2; dan < 10; dan++) { %>
			<tr align="center">		
			<% for(i = 1; i < 10; i ++){ %>
				<% color = dan%2 == 0 ? "tomato" : "lightgray"; %>
				<td bgcolor="<%= color %>">
					<%= dan %> * <%= i %> = <%= dan*i %>
				</td>
			<% } %>
			</tr>		
		<% } %>
		</table>
		
		<br>
		<hr>
		
	</div>
</body>
</html>