<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*"%>
	
<%!
	//[init]
	public void init() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
%>

	<%
		//변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 한글 깨짐 처리
		request.setCharacterEncoding("UTF-8");
		
		// 1. data get (이름, 아이디, 비번, 이메일1, 이메일2, 전번1, 전번2, 전번3, 우편번호, 주소, 상세주소)
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String emailid = request.getParameter("emailid");
		String emaildomain = request.getParameter("emaildomain");
		String tel1 = request.getParameter("tel1");
		String tel2 = request.getParameter("tel2");
		String tel3 = request.getParameter("tel3");
		String zipcode = request.getParameter("zipcode");
		String address = request.getParameter("address");
		String addressdetail = request.getParameter("address_detail");
		
		
		// 2. logic : 1의 data를 DB에 insert
		int cnt = 0;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
			
			StringBuffer sql = new StringBuffer();
			sql.append("insert all \n");
			sql.append("into member(id, name, pass, emailid, emaildomain, joindate) \n");
			sql.append("values(?, ?, ?, ?, ?, sysdate) \n");
			sql.append("into member_detail(id, zipcode, address, address_detail, tel1, tel2, tel3) \n");
			sql.append("values(?, ?, ?, ?, ?, ?, ?) \n");
			sql.append("select * from dual");
			
			pstmt = conn.prepareStatement(sql.toString());
		
			//중간에 set할 ?가 빠지더라도, 숫자를 수정할 필요가 없도록 idx 이용함!
			int idx = 0;
			pstmt.setString(++idx, id);
			pstmt.setString(++idx, name);
			pstmt.setString(++idx, pass);
			pstmt.setString(++idx, emailid);
			pstmt.setString(++idx, emaildomain);
			pstmt.setString(++idx, id);
			pstmt.setString(++idx, zipcode);
			pstmt.setString(++idx, address);
			pstmt.setString(++idx, addressdetail);
			pstmt.setString(++idx, tel1);
			pstmt.setString(++idx, tel2);
			pstmt.setString(++idx, tel3);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 3. response page : 2의 결과에 따라,
		// 	3-1. !=0  (insert 성공 시), 홍길동(name)님 회원가입을 환영합니다.
		//  3-2. ==0  (insert 에러 시), 서버 문제로 회원가입이 실패했습니다.
		//                               다음에 다시 시도해주세요.

		String root = request.getContextPath();
		if(cnt != 0) {	
			//server에서 바로 다른 jsp페이지로 이동하기 위해 java코드로 화면 이동
			response.sendRedirect(root + "/user/registerok.jsp?name="+ URLEncoder.encode(name, "UTF-8"));  // registerok.jsp로 name값 보냄
		} else {
			response.sendRedirect(root + "/user/registerfail.jsp");
		}
		
	%>