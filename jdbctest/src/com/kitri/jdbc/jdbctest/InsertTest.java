package com.kitri.jdbc.jdbctest;

import java.sql.*;

public class InsertTest {

	// [생성자]
	public InsertTest() {
		//드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 완료!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
		
	}
	
	// [메소드]
	// <지정 정보의 DB로 연결한 연결상태를 리턴> 메소드
	public Connection makeConnection() throws SQLException {
		Connection conn = null;
		
		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
		System.out.println("드라이버 연결 완료!");
		
		return conn;
		
	}
	
	// [실행 메소드]
	public static void main(String[] args) {
		
		String name = "신지영";
		String id = "shzy232";
		
		Connection conn = null;
		Statement stmt = null;
		int cnt = 0;
		
		// 1) Driver 로딩
		InsertTest it = new InsertTest();
		
		try {		
			// 2) DB 연결
			conn = it.makeConnection();

			// 3) SQL 실행 준비     //*나중엔 버퍼에 sql문 담는게 좋음.
			String sql = "";
			sql += "insert into jdbctest (no, name, id, joindate) \n";
			sql += "values (jdbctest_no_seq.nextval, '"+ name + "', '" + id  +"', sysdate)";
			
			stmt = conn.createStatement();
			
			// 4) SQL문 실행
			cnt = stmt.executeUpdate(sql);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5) DB 접속 종료
			try {
				if(stmt != null)   //stmt에 값이 들어가기 전에 error 발생 시의 nullPointerError를 방지하기 위함
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.println("data " + cnt + "개 insert!");
		
	}
	
}
