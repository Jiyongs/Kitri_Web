<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String name = request.getParameter("name");
%>

<%@ include file = "/template/header.jsp" %>

<%= name %> 님 안녕하세요.

<%@ include file = "/template/footer.jsp" %>