package com.kitri.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ggd")
public class Gugudan extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// [service]
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("	<body>");
		out.println("	<div align=\"center\">");		
		out.println("		<h1>*** 구구단 ***</h1>");
		out.println("			<table width=\"800\" height=\"700\" border=\"1px\">");
	
		for(int i = 1; i < 10; i++) {
			out.println("			<tr align=\"center\">");
			for(int j = 2; j < 10; j++) {
				String color = j % 2 == 0 ? "tomato" : "skyblue";
				out.println("				<td bgcolor=\"" + color + "\">" + j + " * " + i + " = " + j*i + "</td>");	
			}
			out.println("			</tr>");
		}
		
		out.println("			</table>");
		out.println("	</div>");		
		out.println("	</body>");
		out.println("<html>");
		
	}

}
