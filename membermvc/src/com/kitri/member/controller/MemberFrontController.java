package com.kitri.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.member.model.MemberDetailDto;
import com.kitri.member.model.MemberServiceImpl;
import com.kitri.util.MoveUrl;
import com.kitri.util.SiteConstance;

// web 역할 담당 컨트롤러 (Front controller)
// : 사용자 요구 분석 / 어디로 가라

@WebServlet("/user")
public class MemberFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 파라미터 변수
		String act = request.getParameter("act");
		
		// 이동 경로 설정 변수
		// 경로 잘못 설정시 디폴트값은 index.jsp!
		String path = "/index.jsp";	
		
		// ** act가 null일때 null pointer error가 남!
		//if(act.equals("mvjoin")) {
		//	}
		
		// ** 값을 받아낼 때는 이 방법이 효율적!
		if("mvjoin".equals(act)) {
			
			MoveUrl.redirect(request, response, "/user/member/member.jsp");		
			
		} else if("mvlogin".equals(act)) {
			
			MoveUrl.redirect(request, response, "/user/login/login.jsp");	
			
		} else if("idcheck".equals(act)) {
			// #ajax 작업은 Back Controller에 따로 메소드를 뺴는 것보다
			//  바로 작업하는 것이 좋음!
			
			String sid = request.getParameter("sid");
			String resultXML = MemberServiceImpl.getMemberService().idCheck(sid);
			
			// 클라이언트한테 보내줌 (xml로    => text/xml)   => 받을 때 httpRequest.responseXML   // xml은 무조건 "utf-8"*
			//                       (text로   => text/plain) => 받을 때 httpRequest.responseText
			//                       (json으로 => text/json)  => 받을 때 httpRequest.responseText
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(resultXML);
			
		} else if("zipSearch".equals(act)) {
			String doro = request.getParameter("doro");
			String resultXML = MemberServiceImpl.getMemberService().zipSearch(doro);
			response.setContentType("text/xml;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print(resultXML);

		} else if("register".equals(act)) {
	
			path = MemberController.getMemberController().register(request, response);
			//MoveUrl.redirect(request, response, path); //sendRedirect()는 requset, respose 값을 모두 버리고 path에 감.
			MoveUrl.forward(request, response, path);    //forward()는 requset, respose 값을 가지고 path에 감.
			
			
		} else if("login".equals(act)) {
			
			path = MemberController.getMemberController().login(request, response);
			MoveUrl.redirect(request, response, path);
			
		} else if("logout".equals(act)) {
			
			path = MemberController.getMemberController().logout(request, response);
			MoveUrl.redirect(request, response, path);
			
		}
		
		// get은 클라이언트가 호출할 때마다 돌아감
		
		// 클라이언트의 요구를 분석할 땐 if문이 가장 나음
		// => 각 요구에 맞는 메소드 정의 방법은 어차피, doget에서 다시 호출해야하기 때문에 결국 if문을 써야함
		// ==> 나중에 spring을 쓰면, if문 대신 각각 요구에 맞는 메소드 정의해서 사용할 수 있음
		
		// 서비스 객체는 컨트롤러에서 1개만 만들어져야 함
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(SiteConstance.ENCODE);
		doGet(request, response);
	}

}
