package com.kitri.dto;

import java.io.Serializable;

public class Child extends Parent{

	private String c;
	
	public Child() {
		super();
		// 부모 클래스의 p 객체 초기값 설정
		setP("Parent Instance Variable");
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}
