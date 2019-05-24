package com.kitri.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter(description = "Test", urlPatterns = { "*" })
public class MyFilter implements Filter {

    /**
     * Default constructor. 
     */
    public MyFilter() {
    	System.out.println("MyFilter객체생성됨");
    }
    public void init(FilterConfig fConfig) throws ServletException {    	
		System.out.println("MyFilter의 init()호출");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter의 doFilter()호출됨");
		chain.doFilter(request, response);
	}

	public void destroy() {
		System.out.println("MyFilter객체소멸됨");
	}
	
	

}
