package com.kitri.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dbcounter")
public class DBCounter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// [필드]
	int cnt;

	Connection conn;
	Statement stmt;
	ResultSet rs;

	// [init]
	@Override
	public void init() throws ServletException {
		cnt = 0;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("DB 연결 완료!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// <db의 방문자수 select> 메소드
	public void dbManagerCounter() {

		try {

			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
			stmt = conn.createStatement();

			String sql = "";
			sql += "select no \n";
			sql += "from counter \n";

			rs = stmt.executeQuery(sql);

			// select
			// 반드시 결과가 1개 -> rs.next(); 단독 사용!
			rs.next();
			cnt = rs.getInt("no");

			// update
			sql = "update counter \n";
			sql = "set no = no + 1";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// [service : get]
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		try {

			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
			stmt = conn.createStatement();

			String sql = "";
			sql += "select no \n";
			sql += "from counter \n";

			rs = stmt.executeQuery(sql);

			// select
			// 반드시 결과가 1개 -> rs.next(); 단독 사용!
			rs.next();
			cnt = rs.getInt("no");

			// update
			sql = "update counter \n";
			sql += "set no = no + 1";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		out.println("당신은 " + (cnt + 1) + "번째 방문자입니다.");

	}

}
