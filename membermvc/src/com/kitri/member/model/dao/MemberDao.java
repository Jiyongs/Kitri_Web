package com.kitri.member.model.dao;

import java.util.List;
import java.util.Map;

import com.kitri.member.model.*;

public interface MemberDao {
	
	// *mybatis는 인자를 2개 가질 수 없음! / dto 또는 map을 사용해야 함
	
	// return값 0 : 사용가능 id / !=0 : 사용불가능 id
	int idCheck(String id); 

	// 동 이름 검색시 나오는 모든 주소값들 리턴
	List<ZipcodeDto> zipSearch(String doro); 

	// 회원 가입
	int registerMember(MemberDetailDto memberDetailDto); 

	// 로그인
	MemberDto loginMember(Map<String, String> map); 

	// 회원 1명 정보 얻어오기 (정보 수정 화면에 뿌릴 것들)
	MemberDetailDto getMember(String id); 

	// return 0 : 수정 없음 / !=0 : 수정 함
	int modifyMember(MemberDetailDto memberDetailDto); 

	// return 0 : 삭제 안됨 / !=0 : 삭제 함
	int deleteMember(String id); 

}
