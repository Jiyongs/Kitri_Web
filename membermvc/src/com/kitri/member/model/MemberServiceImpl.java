package com.kitri.member.model;

import java.util.*;

import com.kitri.member.model.dao.MemberDaoImpl;
import com.kitri.member.model.service.MemberService;

public class MemberServiceImpl implements MemberService{

	
	// *[싱글톤 패턴 : 객체를 딱 하나만 만드는 개발 방식]* ////////////////////////////
	
	// *2* 전역 static 변수로 memberservice의 객체 선언
	private static MemberService memberService;
	
	// *3* 클래스가 읽혀지는 시점에 memberservice의 객체 생성!
	static {
		memberService = new MemberServiceImpl();
	}
	
	// *4* memberservice의 객체를 return하는 getter
	public static MemberService getMemberService() {
		return memberService;
	}

	// *1* 외부에서 호출 불가능한 private 생성자
	//  -> 외부에서 서비스 객체를 생성하지 못하게 하기 위함
	private MemberServiceImpl() {}
	
	////////////////////////////////////////////////////////////////////////////////////
	
	// [override method]

	@Override
	public String idCheck(String id) {
		int cnt = MemberDaoImpl.getMemberdao().idCheck(id);
//		System.out.println("cnt = " + cnt);
		String result = "";
		result += "<idcount>\n";
		result += "<cnt>" + cnt + "</cnt>\n";
		result += "</idcount>";
		return result;
	}

	@Override
	public String zipSearch(String doro) {
		
		String result = "";
		List<ZipcodeDto> list = MemberDaoImpl.getMemberdao().zipSearch(doro);
		
		result += "<ziplist>\n";
		
		for(ZipcodeDto zipDto : list) {
			result += "	<zip>\n";
			result += "		<zipcode>" + zipDto.getZipcode() + "</zipcode>\n";
			// <![CDATA[ ... ]]> : [...]를 문자열로 인식하게 하기 위함 / 주소에 &, < 등이 있으면 오류발생하니까!!
			result += "		<address><![CDATA[" + zipDto.getSido() + " " + zipDto.getGugun() + " "  + zipDto.getUpmyun() + " " + zipDto.getDoro() + " " + zipDto.getBuildingNumber() + " " + zipDto.getSigugunBuildingName() + "]]></address>\n";
			result += "	</zip>\n";
		}
		
		result += "</ziplist>";
		
		return result;
	}

	@Override
	public int registerMember(MemberDetailDto memberDetailDto) {
		return MemberDaoImpl.getMemberdao().registerMember(memberDetailDto);
	}

	@Override
	public MemberDto loginMember(String id, String pass) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("userid", id);
		map.put("userpwd", pass);
		
		return MemberDaoImpl.getMemberdao().loginMember(map);
	}

	@Override
	public MemberDetailDto getMember(String id) {
		return null;
	}

	@Override
	public int modifyMember(MemberDetailDto memberDetailDto) {
		return 0;
	}

	@Override
	public int deleteMember(String id) {
		return 0;
	}

}
