package com.kitri.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.util.DBConnection;

@WebServlet("/MemberRegister")
public class MemberRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		1. data get(이름, 아이디, 비번, 이메일1, 이메일2, 전번1, 전번2, 전번3, 우편번호, 주소, 상세주소)

		Connection conn = null;
		PreparedStatement pstmt = null;

		request.setCharacterEncoding("UTF-8");
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

//		2.logic : 1의 data를 DB에 insert
		int cnt = 0;
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("insert all \n");
			sql.append("	into member (id,name,pass,emailid,emaildomain,joindate) \n");
			sql.append("	values(?,?,?,?,?,sysdate) \n");
			sql.append("	into member_detail (id, zipcode,address,address_detail,tel1,tel2,tel3) \n");
			sql.append("	values(?,?,?,?,?,?,?) \n");
			sql.append("select * from dual \n");
			pstmt = conn.prepareStatement(sql.toString());
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
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		3. response page 2의 결과에 따라.
//		3-1. !0 :홍길동님 회원가입을 환영합니다.
//		3-2. 0 : 서버 문제로 회원가입 실패
//				 다시 시도하세요.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<body>");
		if(cnt != 0 ) {
			out.println(name + "님 회원가입을 환영합니다.");
			out.println("로그인 후 서비스를 이용할 수 있습니다<br>");
			out.println("<a href=\"/memberservlet/user/login.html\">로그인</a>");
		}else {
			out.println("<font size=\"13\" color=\"red\">");			
			out.println("서버문제로 회원가입 실패.<br>");			
			out.println("다시 시도해주세요.<br>");
			out.println("</font>");
		}
		out.println("	</body>");
		out.println("</html>");
		System.out.println("회원가입하러옴");
	}

}
