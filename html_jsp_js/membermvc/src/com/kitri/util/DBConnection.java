package com.kitri.util;

import java.sql.*;

public class DBConnection {

	// [static flow]
	// : static은 속한 클래스가 메모리에 올라갈 때(A a1;) 로드가 됨
	//   => 최초의 한 번만 로드!
	//   ex)
	//	 A a1;   *이때 한번만 로드됨
	//   A a2;   *로드 안 됨
	
	// [static block 초기화]
	static {

		// DB Driver 로딩
		try {
			Class.forName(SiteConstance.DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	// [메소드]
	// <DB 연결> 메소드
	public static Connection makeConnection() throws SQLException {
		return DriverManager.getConnection(SiteConstance.DB_URL,SiteConstance.DB_USERNAME, SiteConstance.DB_PASSWORD);
	}

	
}
