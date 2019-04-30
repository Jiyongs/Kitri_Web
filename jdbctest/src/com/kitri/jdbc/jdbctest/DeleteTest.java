package com.kitri.jdbc.jdbctest;

import java.sql.*;

import javax.swing.DefaultButtonModel;

public class DeleteTest {
	
	// [생성자]
	public DeleteTest() {
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("DB 로딩 완료!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	// [메소드]
	// <DB 연결> 메소드
	public Connection makeConnection() throws SQLException {
		
		Connection conn = null;
		
		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
		System.out.println("DB 연결 완료!");
		
		return conn;
		
	}
	
	// [실행 메소드]
	public static void main(String[] args) {
		
		// 1
		DeleteTest dt = new DeleteTest();
		
		Connection conn = null;
		Statement stmt = null;
		int cnt = 0;
		
		String id = "갓지영";
		
		try {
			
			// 2
			conn = dt.makeConnection();
			
			// 3
			String sql = "";
			sql += "delete from jdbctest \n";
			sql += "where id = '" + id + "'";
			
			stmt = conn.createStatement();
			
			// 4
			cnt = stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 5
				if(stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
			System.out.println("data " + cnt + "개 delete!");
		
	}

}
