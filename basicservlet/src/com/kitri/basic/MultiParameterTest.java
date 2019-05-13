package com.kitri.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/multiparam")
public class MultiParameterTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. data get
		// client가 요청하는 data를 관리하는 파라미터 : request
		// request를 통해 data를 name단위로 얻어오기 : ServletRequest 인터페이스의 getParameter() 메소드
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		int age = Integer.parseInt(request.getParameter("age"));
		
		// request를 통해 data를 value단위로 얻어오기 : ServletRequest 인터페이스의 getParameterValues() 메소드
		String[] fruits = request.getParameterValues("fruit");
		
		int len = 0;
		if(fruits!=null)   //nullpointer error 방지
			len = fruits.length;
		
		// 2. logic
		String color = age == 10 ? "pink" : "cyan";
		String likedfruits = "";
		if(len==1) {
			likedfruits = fruits[0];
		} else if(len>1) {
			for(int i = 0; i <len-1; i++) {
				likedfruits += fruits[i] + ", ";				
			}
			likedfruits += fruits[len-1];
		}
		
		
		// 3. response page
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("	<body>");
		out.println(name + "(<font color=\"" + color + "\">" + id + "</font>)님 안녕하세요!<br>");

		
		// 당신이 좋아하는 과일은
		// 1개 선택 : 사과입니다.          *사과랑 입니다 붙여야 함
		// 2개 선택 : 사과, 바나나입니다.  *사과, 바나나 공백 주의!
		// ...
		// 4개 선택 : 사과, 바나나, 수박, 딸기입니다.
		// 미 선택 : 없습니다.
		out.println("당신이 좋아하는 과일은 ");
		if(len == 0) {
			out.println("없습니다.");			
		} else {
			out.println(likedfruits + "입니다."); 
		}		
		
		out.println("	</body>");
		out.println("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}


}
