package com.kitri.guestbook;

import java.io.IOException;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/gbwrite")
public class GuestBookWrite extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 변수 선언
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		// 1. get data
		String author = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		// 2. logic : 1의 data를 db에 insert
		
		try {
			// db 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","kitri","kitri");
			// sql문 준비
			StringBuffer sql = new StringBuffer();
			sql.append("insert into guestbook \n");
			sql.append("values(guestbook_seq.nextval, ?, ?, ?, sysdate)");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			int idx = 0;
			pstmt.setString(++idx, author);
			pstmt.setString(++idx, subject);
			pstmt.setString(++idx, content);
			
			// sql문 실행
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// db 종료
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		// 3. response page
		//    : /guestbookservlet/guestbook/list.html로 이동
		response.setContentType("text/html;charset=UTF-8");
		
		if(cnt != 0) {
			//list.html로 이동
			RequestDispatcher rd = request.getRequestDispatcher("/guestbookservlet/gblist"); 
			rd.include(request, response);
		} else {
			// 오류 페이지로 이동
			System.out.println("db에 안됐다");
		}
		
	}

}