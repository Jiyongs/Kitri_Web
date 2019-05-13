<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kitri.member.model.MemberDto,com.kitri.util.MoveUrl"%>

<%@ include file = "/template/header.jsp" %>

<%
	//String name = request.getParameter("name");
	MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");
	
	if(memberDto != null) {
%>

<script type="text/javascript">
function deleteMember() {
	if(confirm("정말 탈퇴하시겠습니까?")){
		document.location.href = "<%=root%>/user?act=deleteMember";
		// 나중에 만들기 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	}
}
</script>

<strong><%=memberDto.getName()%>(<%=memberDto.getId()%>)님 안녕하세요.</strong>
<a href="<%=root%>/user?act=logout">로그아웃</a>
<a href="<%=root%>/user?act=mvmodify">정보수정</a>
<a href="#" onclick="javascript:deleteMember();">회원탈퇴</a>
<%
		// 관리자 아이디로 가정
			if("shzy232".equals(memberDto.getId())){
	
%>

				<a href="<%=root%>/admin?act=memberlist">관리자</a>

<%
			} else {
				MoveUrl.redirect(request, response, "/user?act=mvlogin");
			}

	}else{
  		 MoveUrl.redirect(request, response, "/user?act=mvlogin");
	}
	// 문제
	// : 로그인이 풀려있는 것처럼 보임
	// -> 로그인 정보를 request에 담았기 때문
	// -> 컨트롤러를 수정
	
%>

<%@ include file = "/template/footer.jsp" %>