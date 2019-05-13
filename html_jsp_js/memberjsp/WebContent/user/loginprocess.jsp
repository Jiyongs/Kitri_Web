<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.*, java.net.URLEncoder"%>

	<%
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String name = null;

		request.setCharacterEncoding("UTF-8");
		
		// 1. get data
		String inputId = request.getParameter("id");
		String inputPass = request.getParameter("pass");

		// 2. logic : 1의 data를 가지고 select
		// db에서 id와 pass select
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");

			StringBuffer sql = new StringBuffer();
			sql.append("select name \n");
			sql.append("from member \n");
			sql.append("where id = ? and pass = ? \n");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPass);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				name = rs.getString("name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 3. response page
		//    id, pass 일치 시, ooo님 안녕하세요. 출력
		//    id, pass 불일치 시, 아이디 또는 비밀번호를 다시 확인하세요.
		//                        등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력했습니다.

		String root = request.getContextPath();
		
		if (name != null) {
			// 로그인 완료
			response.sendRedirect(root + "/user/loginok.jsp?name="+URLEncoder.encode(name, "UTF-8"));
		} else {
			// 로그인 실패
			response.sendRedirect(root + "/user/loginfail.jsp");
		}
	%>