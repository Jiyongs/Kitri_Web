package com.kitri.member.model.service;

import java.util.List;

import com.kitri.member.model.*;

public interface MemberService {

	String idCheck(String id); //return값 0 : 사용가능 id / !=0 : 사용불가능 id	
	
	String zipSearch(String doro); // 동 이름 검색시 나오는 모든 주소값들 리턴
	
	int registerMember(MemberDetailDto memberDetailDto); // 회원 가입
	
	MemberDto loginMember(String id, String pass); //로그인
	
	//혼자 해보기!!
	MemberDetailDto getMember(String id);  // 회원 1명 정보 얻어오기 (정보 수정 화면에 뿌릴 것들)
	int modifyMember(MemberDetailDto memberDetailDto);   // return 0 : 수정 없음 / !=0 : 수정 함
	int deleteMember(String id);   // return 0 : 삭제 안됨 / !=0 : 삭제 함
	
}
