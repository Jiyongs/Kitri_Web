package com.kitri.jdbc.jdbctest;

import java.sql.*;

//shzy232의 가입일을 현재시간으로 수정
public class UpdateTest {

	// [생성자]
	public UpdateTest() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 완료!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// [메소드]
	// <드라이버 연결> 메소드
	public Connection makeConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
		System.out.println("드라이버 연결 완료!");
		
		return conn;
	}
	
	// [실행 메소드]
	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		int cnt = 0;
		
		// 1
		UpdateTest ut = new UpdateTest();
		
		try {
			
			// 2
			conn = ut.makeConnection();
			
			// 3
			String id = "shzy232";
			
			String sql = "";
			sql += "update jdbctest \n";
			sql += "set joindate = sysdate \n";
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
		
		System.out.println("data " + cnt + "개 update!");
		
	}
	
}
