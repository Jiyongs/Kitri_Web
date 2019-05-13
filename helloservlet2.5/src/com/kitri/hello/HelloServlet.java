package com.kitri.hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 코드 상에서 한글 깨짐 처리 방법 ①
		// contentType을 지정 (나는 text로 코딩하지만, 브라우저는 html로 인식해라, 인코딩은 UTF-8로 하겠다)
		// *getWriter보다 먼저 와야 함!
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<body>");
		out.println("Hello Servlet!!<br>");
		out.println("안녕 서블릿!!");
		out.println("</body>");
		out.println("</html>");	
		
	}

}
