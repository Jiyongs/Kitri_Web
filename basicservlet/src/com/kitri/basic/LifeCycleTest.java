package com.kitri.basic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/life")
public class LifeCycleTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// [생성자]
	// 서블릿의 생성자도 가능함. 일반적으로 초기화를 init()에서 하기 때문에, 잘 사용하지는 않는다.
	// 서블릿은 was가 실행하는 것이기 때문에, 생성자에 인자값을 줘도 내맘대로 사용할 수 없음.
	public LifeCycleTest() {
		System.out.println("생성자() 호출!!!");		
	}
	
	// [메소드]
	// <init : 초기화 시 실행> 메소드
	@Override
	public void init() throws ServletException {
		System.out.println("init() 호출!!!");
	}

	// <deGet : Service 수행> 메소드
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("service() 호출!!!");
	}

	// <destroy : 종료 시 실행> 메소드
	@Override
	public void destroy() {
		System.out.println("destroy() 호출!!!");
	}

}
