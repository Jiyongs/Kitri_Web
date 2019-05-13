package com.kitri.member.controller;

import javax.servlet.http.*;

import com.kitri.member.model.*;
import com.kitri.member.model.service.MemberService;

// Business Logic 담당 컨트롤러 (Back Controller)
// : Front Controller 역할을 제외한 나머지 작업 수행
public class MemberController {

	// **싱글톤 패턴 적용
	private static MemberController memberController;
	
	static {
		memberController = new MemberController();
	}
	
	// [생성자]
	private MemberController() {}

	public static MemberController getMemberController() {
		return memberController;
	}

	// [메소드]
	// <회원가입> 메소드
	public String register(HttpServletRequest request, HttpServletResponse response) {
		
		String path = "/index.jsp";
		
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
		
		if(cnt != 0) {
			// jsp의 기본 메소드 사용하여 memberDetailDto를 request페이지로 보냄
			request.setAttribute("userInfo", memberDetailDto); // memberDetailDto는 registerok.jsp에서만 사용되고 말 것이므로 request에 setA함
			path ="user/member/registerok.jsp";
		} else {
			path ="user/member/registerfail.jsp";
		}
		
		return path;
		
	}

	// <로그인> 메소드
	public String login(HttpServletRequest request, HttpServletResponse response) {

		String path = "/index.jsp";
		
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		
		MemberDto memberDto = MemberServiceImpl.getMemberService().loginMember(id, pass);
		
		// 로그인 했을 경우,
		if(memberDto != null) {
			
			///////////////////////// cookie ////////////////////////
			
			// '아이디 저장' 체크박스가 체크됐을 경우, 쿠키 저장
			String idsv = request.getParameter("idsave");
			if("idsave".equals(idsv)) {
			
				Cookie cookie = new Cookie("kid_inf", id);
				cookie.setDomain("localhost");
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(60*60*24*365*50); 			// *50년
			
				// cookie를 client쪽에 보냄
				response.addCookie(cookie);
			}
			// '아이디 저장' 체크박스가 해제됐을 경우, 쿠키 제거
			else {
				Cookie cookie[] = request.getCookies();
				
				// 쿠키가 비어있지 않을 경우
				if(cookie != null) {
					for(Cookie c : cookie) {
						
						// 쿠키들 중, 이름이 "kid_inf"인 쿠키의 값을 얻어옴
						if("kid_inf".equals(c.getName())){
							c.setDomain("localhost");
							c.setPath(request.getContextPath());
							c.setMaxAge(0); 			// *만료일을 0으로 설정하여, 쿠키를 없앰
						
							// cookie를 client쪽에 보냄
							response.addCookie(c);
							break;
						}
						
					}
				}
			}
			
			///////////////////////// cookie ////////////////////////			

			///////////////////////// session ////////////////////////			
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", memberDto);
			///////////////////////// session ////////////////////////
			
			path ="/user/login/loginok.jsp";
		} else {
			path ="/user/login/loginfail.jsp";
		}

		return path;
		
	}
	
	// <로그아웃> 메소드
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		//session.setAttribute("userInfo", null);
//		session.removeAttribute("userInfo");
		session.invalidate();  //세션의 모든것을 무효화 / 삭제
		return "/user/login/login.jsp";
	}
	
	
}
