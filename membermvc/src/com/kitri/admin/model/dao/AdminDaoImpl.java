package com.kitri.admin.model.dao;

import java.sql.*;
import java.util.*;

import com.kitri.member.model.MemberDetailDto;
import com.kitri.util.DBClose;
import com.kitri.util.DBConnection;

public class AdminDaoImpl implements AdminDao{

	private static AdminDao adminDao;
	
	static {
		adminDao = new AdminDaoImpl();
	}
	
	private AdminDaoImpl() {}
	
	public static AdminDao getAdminDao() {
		return adminDao;
	}
	
	@Override
	public List<MemberDetailDto> getMemberList(Map<String, String> map) {
		
		List<MemberDetailDto> list = new ArrayList<MemberDetailDto>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("select m.name, m.id, m.emailid, m.emaildomain, m.joindate, \n");
			sql.append("		d.tel1, d.tel2, d.tel3, d.zipcode, d.address, d.address_detail \n");
			sql.append("from member m, member_detail d \n");
			sql.append("where m.id = d.id \n");
			
			String key = map.get("key");
			String word = map.get("word");
			
			// 검색어가 있는 경우만
			if(word != null && !word.isEmpty()) {
				//검색한 id & 검색한 전화번호 뒷자리, 주소는 포함
				// 검색 조건이 id인 경우
				if("id".equals(key)) {
					sql.append("and m.id = ? \n");
				} else { // 검색 조건이 id가 아닌 나머지인 경우
					sql.append("and m." + key + " like '%'||?||'%' \n");
				}
			}

			pstmt = conn.prepareStatement(sql.toString());
			
			// 검색어가 있는 경우만
			if(word != null && !word.isEmpty()) {
				pstmt.setString(1, word);
			}
			
			rs = pstmt.executeQuery();

			
			while(rs.next()) {
				
				MemberDetailDto memberDetailDto = new MemberDetailDto();
				
				memberDetailDto.setName(rs.getString("name"));
				memberDetailDto.setId(rs.getString("id"));
				memberDetailDto.setEmailid(rs.getString("emailid"));
				memberDetailDto.setEmaildomain(rs.getString("emaildomain"));
				memberDetailDto.setJoindate(rs.getString("joindate"));
				memberDetailDto.setTel1(rs.getString("tel1"));
				memberDetailDto.setTel2(rs.getString("tel2"));
				memberDetailDto.setTel3(rs.getString("tel3"));
				memberDetailDto.setZipcode(rs.getString("zipcode"));
				memberDetailDto.setAddress(rs.getString("address"));
				memberDetailDto.setAddressDetail(rs.getString("address_detail"));
				
				list.add(memberDetailDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return list;
	}

}
