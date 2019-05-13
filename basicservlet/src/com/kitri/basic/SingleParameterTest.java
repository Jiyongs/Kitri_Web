package com.kitri.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/singleparam")
public class SingleParameterTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. date get
		// client가 요청하는 data를 관리하는 파라미터 : request
		// request를 통해 data를 input단위로 얻어오기 : ServletRequest 인터페이스의 getParameter() 메소드
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		int age = Integer.parseInt(request.getParameter("age"));
		
		// 2. logic 처리
		String color = age == 10 ? "pink" : "cyan";
		
		// 3. response page 생성
		// : 안효인(java2)님 안녕하세요
		// 10대 이하면, id를 빨간색
		// 20대 이상이면, id를 파란색으로 표시!
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<body>");
		out.println(name + "(<font color=\"" + color + "\">" + id + "</font>)님 안녕하세요!");
		out.println("	</body>");
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
	
	

}
