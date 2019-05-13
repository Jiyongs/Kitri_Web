<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 프로젝트 안에 있는 파일을 기준으로 가져오기 때문에, memberjsp라는 프로젝트 이름은 작성하지 않아도 됨!!! -->
<%@ include file="/template/header.jsp" %>

<script type="text/javascript">
$(document).ready(function() {

});

function login() {
	if(document.getElementById("id").value == "") {
		alert("아이디를 입력해주세요!");
		return;
	} else if(document.getElementById("pass").value == "") {
		alert("비밀번호를 입력해주세요!");		
		return;
	} else {
		document.getElementById("loginform").action = "<%= root %>/user/loginprocess.jsp";
		document.getElementById("loginform").submit();
	}
}

function mvjoin(){
	document.location.href = "<%= root %>/user/member.jsp";
}
</script>

<body>

<div class="container" align="center">
	<div class="col-lg-6" align="center">
		<h2>로그인</h2>
		<form id="loginform" method="post" action="">
			<div class="form-group" align="left">
				<label for="">아이디</label>
				<input type="text" class="form-control" id="id" name="id" placeholder="">
			</div>
			<div class="form-group" align="left">
				<label for="">비밀번호</label>
				<input type="password" class="form-control" id="pass" name="pass" placeholder="">
			</div>
			<div class="form-group" align="center">
				<button type="button" class="btn btn-warning" id="loginBtn" onclick="javascript:login();">로그인</button>
				<button type="button" class="btn btn-primary" id="moveRegisterBtn" onclick="javascript:mvjoin();">회원가입</button>
			</div>
		</form>
	</div>
</div>
<%@ include file="/template/footer.jsp" %>