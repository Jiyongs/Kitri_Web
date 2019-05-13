package com.kitri.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/counter")
public class Counter extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	int cnt;
	int totalLen;
	
	public void init() {
		cnt = 0;
		totalLen = 8;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		cnt++; 
			
		out.println("<html>");
		out.println("	<body>");
		out.println("당신은 ");
		
		// String str = cnt + "";
		String str = Integer.toString(cnt);
		int len = str.length();		
		
		for(int i= 0; i < totalLen-len; i ++) {
			out.println("<img src=\"/basicservlet/img/0.PNG\">");			
		}
		for(int i = 0; i < len; i++) {
			out.println("<img src=\"/basicservlet/img/" + str.charAt(i) +".PNG\">");
		}
		
		out.println("번째 방문자입니다.");		
		out.println("	</body>");
		out.println("</html>");
	}

}
