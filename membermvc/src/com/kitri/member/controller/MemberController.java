package com.kitri.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kitri.member.model.MemberDetailDto;
import com.kitri.member.model.MemberServiceImpl;
import com.kitri.util.SiteConstance;

@WebServlet("/user")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String act = request.getParameter("act");
		
		// ** act가 null일때 null pointer error가 남!
		//if(act.equals("mvjoin")) {
		//	}
		
		// ** 값을 받아낼 때는 이 방법이 효율적!
		if("mvjoin".equals(act)) {
			response.sendRedirect("/membermvc/user/member/member.jsp");			
		} else if("mvlogin".equals(act)) {
			response.sendRedirect("/membermvc/user/login/login.jsp");						
		} else if("idcheck".equals(act)) {
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
	
			MemberDetailDto memberDetailDto = new MemberDetailDto();
			
			// 이름, 아이디, 비번, 이메일1, 이메일2, 전번1, 전번2, 전번3, 우편번호, 주소, 상세주소
			memberDetailDto.setName(request.getParameter("name"));
			memberDetailDto.setId(request.getParameter("id"));
			memberDetailDto.setPass(request.getParameter("pass"));
			memberDetailDto.setEmailid(request.getParameter("emailid"));
			memberDetailDto.setEmaildomain(request.getParameter("emaildomain"));
			memberDetailDto.setTel1(request.getParameter("tel1"));
			memberDetailDto.setTel2(request.getParameter("tel2"));
			memberDetailDto.setTel3(request.getParameter("tel3"));
			memberDetailDto.setZipcode(request.getParameter("zipcode"));
			memberDetailDto.setAddress(request.getParameter("address"));
			memberDetailDto.setAddressDetail(request.getParameter("address_detail"));
			
			int cnt = MemberServiceImpl.getMemberService().registerMember(memberDetailDto);
			
		} else if("".equals(act)) {
			
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
