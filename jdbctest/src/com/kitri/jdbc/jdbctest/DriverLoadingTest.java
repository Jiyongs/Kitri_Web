package com.kitri.jdbc.jdbctest;

public class DriverLoadingTest {
	
	public DriverLoadingTest() {
		//java.lang 패키지 - Class 클래스 - forName() 메소드 사용
		// : forName의 인자로 등록된 클래스를 메모리에 올려주는 역할
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loading success!!!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// 생성자로 Driver 로딩
		new DriverLoadingTest();
	}
	
}
