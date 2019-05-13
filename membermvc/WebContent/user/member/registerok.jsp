<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kitri.member.model.MemberDetailDto"%>
<%@ include file = "/template/header.jsp" %>

<%
	//String name = request.getParameter("name"); // register.jsp에서 보낸 파라미터값인 name을 받음
	MemberDetailDto memberDetailDto = (MemberDetailDto) request.getAttribute("userInfo");
%>

<body>
	<strong><%= memberDetailDto.getName() %></strong>님 회원가입을 환영합니다.<br>
	가입하신 정보는 아래와 같습니다.<br>
	아이디 : <%= memberDetailDto.getId() %> <br>
	이메일 : <%= memberDetailDto.getEmailid()%>@<%= memberDetailDto.getEmaildomain() %> <br>
	전화번호 : <%= memberDetailDto.getTel1() %>-<%= memberDetailDto.getTel2() %>-<%= memberDetailDto.getTel3() %><br>
	주소 : <%= memberDetailDto.getAddress() %> <%= memberDetailDto.getAddressDetail() %><br>
	로그인 후, 모든 서비스를 이용할 수 있습니다.<br>
	<a href="<%= root %>/user/login/login.jsp">로그인</a>
	
<%@ include file = "/template/footer.jsp" %>
