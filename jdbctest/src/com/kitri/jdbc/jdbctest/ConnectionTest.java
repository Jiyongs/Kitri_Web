package com.kitri.jdbc.jdbctest;

import java.sql.*;

public class ConnectionTest {

//	[생성자]
	public ConnectionTest() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Connection success!!!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
//	[메소드]
	private void dbConnect() {
		Connection conn = null;
		try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
//	[실행 메소드]
	public static void main(String[] args) {
		
		ConnectionTest ct = new ConnectionTest();
		ct.dbConnect();
		
	}

}
