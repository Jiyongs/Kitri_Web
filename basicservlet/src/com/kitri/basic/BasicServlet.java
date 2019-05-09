package com.kitri.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/basic")
public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String name;
	int age;
	
	@Override
	public void init() throws ServletException {
		// init() 용도 1
		// 전역 변수의 초기값 지정
		name = "홍길동";
		age = 25;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// [로직 추가]
		String color = age <= 18 ? "red" : "blue";
		
		// [응답 화면에 출력하기]
		// 1. 응답하려는 컨텐츠의 타입 지정 & 한글 인코딩
		response.setContentType("text/html;charset=UTF-8");
		// 2. 출력객체 생성
		PrintWriter out = response.getWriter();
		// 3. 출력객체에 출력할 내용 지정
		out.println("<html>");
		out.println("	<body>");
			out.println("<font color=\"steelblue\">" + name + "</font>(<font color=\"" + color + "\">" + age + "</font>)님 안녕하세요");			
		out.println("	</body>");
		out.println("</html>");
	}


}
