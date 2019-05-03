package com.kitri.guestbook;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GuestBookList
 */
@WebServlet("/gblist")
public class GuestBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. logic : guestbook 테이블의 모든 data select (seq, name, subject, content, logtime)
		
		// db에 있는 모든 글을 select해서
		// 글목록에 띄우기
		// 페이징 처리는 나중에 할 것이므로 하지 말기!
	}

}
