<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>jstl.jsp</title>
</head>
<body>

<c:set var="num" value="${param.num}" />
요청전달 데이터 : ${num} <br>

<c:if test="${num%2==0}">
짝수입니다. <br>
</c:if>

<c:choose>
	<c:when test="${num%2 == 0}">
		짝수입니다.
	</c:when>
	<c:otherwise>
		홀수입니다.
	</c:otherwise>
</c:choose>

<hr>
<c:forEach begin="1" end="10" step="1" var="i">
	i값 : ${i} <br>
</c:forEach>

<hr>
<c:set var="total" value="0"/>
<c:forEach begin="1" end="10" var="i">
	<c:set var="total" value="${total+i}"/>
</c:forEach>
1 ~ 10의 합 : ${total}

<hr>
<%
	List<String> list = new ArrayList<>();
	list.add("one");
	list.add("two");
	list.add("three");
	request.setAttribute("list", list);
%>

<c:forEach var="e" items="${requestScope.list}">
	${e} <br>
</c:forEach>

<hr>
<c:forEach var="e" items="${requestScope.list}" varStatus="obj">
	${obj.index} : ${e} ${obj.count}번째 반복중<br>
</c:forEach>

</body>
</html>