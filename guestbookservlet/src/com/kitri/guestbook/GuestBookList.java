package com.kitri.guestbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Servlet implementation class GuestBookList
 */
@WebServlet("/gblist")
public class GuestBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		ArrayList<GuestBookDto> list = new ArrayList<GuestBookDto>();
		
		// 1. logic : guestbook 테이블의 모든 data select (seq, name, subject, content, logtime)
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "kitri", "kitri");
			StringBuffer sql = new StringBuffer();
			sql.append("select * from guestbook");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				GuestBookDto gdto = new GuestBookDto();

				gdto.setSeq(rs.getInt("seq"));
				gdto.setAuthor(rs.getString("name"));
				gdto.setSubject(rs.getString("subject"));
				gdto.setContent(rs.getString("content"));
				gdto.setLogtime(rs.getString("logtime").substring(0, 10));
				
				list.add(gdto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(rs != null)
						rs.close();
					if(stmt != null)
						stmt.close();
					if(conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
		}
		
		// db에 있는 모든 글을 select해서
		// 글목록에 띄우기
		// 페이징 처리는 나중에 할 것이므로 하지 말기!
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>\r\n" + 
				"<html lang=\"ko\">\r\n" + 
				"<head>\r\n" + 
				"<title>글목록</title>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css\">\r\n" + 
				"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n" + 
				"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js\"></script>\r\n" + 
				"<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js\"></script>\r\n" + 
				"<script type=\"text/javascript\">\r\n" + 
				"</script>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"\r\n" + 
				"<div class=\"container\" align=\"center\">\r\n" + 
				"  <div class=\"col-lg-8\" align=\"center\">\r\n" + 
				"  <h2>글목록</h2>"
				+ "<table class=\"table table-borderless\">\r\n" + 
				"  	<tr>\r\n" + 
				"  		<td align=\"right\"><button type=\"button\" class=\"btn btn-link\" >글쓰기</button></td>\r\n" + 
				"  	</tr>\r\n" + 
				"  </table>");
		
		int len = list.size();
		
		if(len != 0) {
		for(int i = 0; i < len; i++) {
			out.println("	<table class=\"table table-active\">\r\n" + 
					"    <tbody>\r\n" + 
					"      <tr>\r\n");
			out.println("        <td>작성자 :");
			out.println(list.get(i).getAuthor()+"</td>\r\n");
			out.println(
					"        <td style=\"text-align: right;\">작성일 : ");
			out.println(list.get(i).getLogtime()+"</td>\r\n" + 
					"      </tr>\r\n" + 
					"      <tr>\r\n" + 
					"        <td colspan=\"2\"><strong>");
			out.println(list.get(i).getSeq() + ". " + list.get(i).getSubject() + "</strong></td>\r\n" + 
					"      </tr>\r\n" + 
					"      <tr>\r\n" + 
					"        <td colspan=\"2\">");
			out.println(list.get(i).getContent() + "</td>\r\n" + 
					"      </tr>\r\n" + 
					"    </tbody>\r\n" + 
					"  </table>");
			
			}
		} else {
			out.println("<span>불러올 글이 없습니다.</span>");
		}
		
		out.println("	  </div>\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>");
	}

}
