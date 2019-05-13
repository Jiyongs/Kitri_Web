package com.kitri.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MemberLogin")
public class MemberLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl","kitri", "kitri");
			
			StringBuffer sql = new StringBuffer();
			sql.append("select name \n");
			sql.append("from member \n");
			sql.append("where id = ? and pass = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPass);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				name = rs.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// 3. response page
		//    id, pass 일치 시, ooo님 안녕하세요. 출력
		//    id, pass 불일치 시, 아이디 또는 비밀번호를 다시 확인하세요.
		//                        등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력했습니다.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<body>");
		
		if(name != null) {
			// 로그인 완료
			out.println(name + "님 안녕하세요.");
		} else {
			// 로그인 실패
			out.println("<font color=\"red\">아이디 또는 비밀번호를 다시 확인하세요. <br>");			
			out.println("등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력했습니다.</font>");			
		}
		
		out.println("	</body>");
		out.println("</html>");
		
	}

}

