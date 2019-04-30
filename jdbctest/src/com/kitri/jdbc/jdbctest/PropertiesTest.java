package com.kitri.jdbc.jdbctest;

import java.io.*;
import java.util.Properties;

// Collection 인터페이스 - Properties 예제
// Properties 파일은 문자열로 된 key와 value로 이루어진 파일 - 'test.properties'
public class PropertiesTest {

	public static void main(String[] args) {
		Properties prop = new Properties(); //Properties 객체 생성
		
		try {
			prop.load(new FileReader(new File("src\\com\\kitri\\jdbc\\jdbctest\\test.properties")));  //test.properties 파일 읽어오기
			String name = prop.getProperty("name_kor"); //key가 name_for인 value를 얻어오기
			System.out.println(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}

}
