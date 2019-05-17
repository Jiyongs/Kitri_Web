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

import com.kitri.util.DBClose;
import com.kitri.util.DBConnection;


@WebServlet("/MemberLogin")
public class MemberLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("loadding sueccess!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//		1. data get (아이디, 비밀번호)
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String name = null;
		
//		2.logic 1의 data를 select
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select name \n");
			sql.append("	from customer \n");
			sql.append("where id=? and pass=? \n");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{		
			DBClose.close(conn, pstmt, rs);
		}
		
//		3. response page
//		3-1. !- null : 홍길동님 안녕하세요.
//		3-2 ==null : 아이디또는 비밀번호를 확인하세요.
//		네이버에 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하였습니다.
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<body>");
		if(name != null) {
			out.println("<storng>" +name + "</strong>" +"님 안녕하세요.");
		} else {
			out.println("<font size=\"13\" color=\"red\">");			
			out.println("아이디 또는 비밀번호를 확인하세요.<br>");			
			out.println("네이버에 등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하였습니다.<br>");
			out.println("<a href=\"/myjquery/login2.html\">로그인</a>");
			out.println("</font>");
		}
		out.println("	</body>");
		out.println("</html>");
	}

}
