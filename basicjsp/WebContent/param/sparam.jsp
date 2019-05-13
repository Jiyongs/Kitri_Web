<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		//1. date get
		// client가 요청하는 data를 관리하는 파라미터 : request
		// request를 통해 data를 input단위로 얻어오기 : ServletRequest 인터페이스의 getParameter() 메소드
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		int age = Integer.parseInt(request.getParameter("age"));

		// 2. logic 처리
		String color = age == 10 ? "pink" : "cyan";

		// 3. response page 생성
		// : 안효인(java2)님 안녕하세요
		// 10대 이하면, id를 빨간색
		// 20대 이상이면, id를 파란색으로 표시!
		response.setContentType("text/html;charset=UTF-8");
	%>
	<%= name %>(<font color = "<%= color %>"> <%= id %> </font>)님 안녕하세요!

</body>
</html>