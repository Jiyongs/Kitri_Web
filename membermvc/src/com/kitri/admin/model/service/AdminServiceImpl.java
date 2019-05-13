package com.kitri.admin.model.service;

import java.util.*;

import com.kitri.admin.model.dao.AdminDaoImpl;
import com.kitri.member.model.MemberDetailDto;

public class AdminServiceImpl implements AdminService{

	private static AdminService adminService;
	
	static {
		adminService = new AdminServiceImpl();
	}
	
	private AdminServiceImpl() {}
	
	public static AdminService getAdminService() {
		return adminService;
	}

	// DB에서 회원 목록 얻어오기
	@Override
	public String getMemberList(String key, String word) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", key);
		map.put("word", word);
		List<MemberDetailDto> list = AdminDaoImpl.getAdminDao().getMemberList(map);
		
		// 얻어온 값으로 XML 만듦
		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		result += "<memberlist>\n";
		for(MemberDetailDto memberDetailDto : list) {
			result += "	<member>\n";
			result += "		<id>" + memberDetailDto.getId() + "</id>\n";
			result += "		<name>" + memberDetailDto.getName() + "</name>\n";
			result += "		<email>" + memberDetailDto.getEmailid() + "@" + memberDetailDto.getEmaildomain() + "</email>\n";
			result += "		<tel>" + memberDetailDto.getTel1() + "-" + memberDetailDto.getTel2() + "-" + memberDetailDto.getTel3() + "</tel>\n";
			result += "		<address><![CDATA[" + memberDetailDto.getZipcode() + " " + memberDetailDto.getAddress() + " " + memberDetailDto.getAddressDetail() + "]]></address>\n";
			result += "		<joindate>" + memberDetailDto.getJoindate() + "</joindate>\n";
			result += "	</member>\n";
		}
		result += "</memberlist>";
		
		return result;
	}

}
