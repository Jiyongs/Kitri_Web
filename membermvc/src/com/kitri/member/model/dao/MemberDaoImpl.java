package com.kitri.member.model.dao;

import java.sql.*;
import java.util.*;

import com.kitri.member.model.*;
import com.kitri.util.DBClose;
import com.kitri.util.DBConnection;

public class MemberDaoImpl implements MemberDao{

	// *[싱글톤 패턴]*
	
	// *2*
	private static MemberDao memberdao;
	
	// *3*
	static {
		memberdao = new MemberDaoImpl();
	}
	
	// *4*
	public static MemberDao getMemberdao() {
		return memberdao;
	}
	
	// *1*
	private MemberDaoImpl() {}
	
	
	// [override method]

	// <id 중복 여부 판별> 메소드
	@Override
	public int idCheck(String id) {
		int cnt = 1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(id) \n");
			sql.append("from member \n");
			sql.append("where id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			cnt = rs.getInt(1);
			
		} catch (SQLException e) {
			cnt = 1; // 예외 발생 시, id 사용 불가로 설정
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}	
	
		return cnt;
	}

	// <주소검색> 메소드
	@Override
	public List<ZipcodeDto> zipSearch(String doro) {
		List<ZipcodeDto> list = new ArrayList<ZipcodeDto>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select 	new_post_code zipcode, sido_kor sido, gugun_kor gugun,  \n "
					+ "nvl(upmyon_kor, ' ') upmyon, doro_kor doro, "
					+ "case when building_refer_number != '0'"
					+ "then building_origin_number||'-'||building_refer_number "
					+ "else trim(to_char(building_origin_number, '99999'))"
					+ "end building_number, sigugun_building_name \n"
					+ "from 	postcode \n"
					+ "where 	doro_kor like '%'||?||'%' " // -> %doro%
					+ "or sigugun_building_name like '%'||?||'%'");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, doro);
			pstmt.setString(2, doro);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ZipcodeDto zipDto = new ZipcodeDto();
				zipDto.setZipcode(rs.getString("zipcode"));
				zipDto.setSido(rs.getString("sido"));
				zipDto.setGugun(rs.getString("gugun"));
				zipDto.setUpmyun(rs.getString("upmyon"));
				zipDto.setDoro(rs.getString("doro"));
				zipDto.setBuildingNumber(rs.getString("building_number"));
				zipDto.setSigugunBuildingName(rs.getString("sigugun_building_name"));
				
				list.add(zipDto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
			
		return list;
	}

	// <회원가입> 메소드
	@Override
	public int registerMember(MemberDetailDto memberDetailDto) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.makeConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("insert all \n");
			sql.append("into member(id, name, pass, emailid, emaildomain, joindate) \n");
			sql.append("values(?, ?, ?, ?, ?, sysdate) \n");
			sql.append("into member_detail(id, zipcode, address, address_detail, tel1, tel2, tel3) \n");
			sql.append("values(?, ?, ?, ?, ?, ?, ?) \n");
			sql.append("select * from dual");
			
			pstmt = conn.prepareStatement(sql.toString());
		
			//중간에 set할 ?가 빠지더라도, 숫자를 수정할 필요가 없도록 idx 이용함!
			int idx = 0;
			pstmt.setString(++idx, memberDetailDto.getId());
			pstmt.setString(++idx, memberDetailDto.getName());
			pstmt.setString(++idx, memberDetailDto.getPass());
			pstmt.setString(++idx, memberDetailDto.getEmailid());
			pstmt.setString(++idx, memberDetailDto.getEmaildomain());
			pstmt.setString(++idx, memberDetailDto.getId());
			pstmt.setString(++idx, memberDetailDto.getZipcode());
			pstmt.setString(++idx, memberDetailDto.getAddress());
			pstmt.setString(++idx, memberDetailDto.getAddressDetail());
			pstmt.setString(++idx, memberDetailDto.getTel1());
			pstmt.setString(++idx, memberDetailDto.getTel2());
			pstmt.setString(++idx, memberDetailDto.getTel3());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				DBClose.close(conn, pstmt);
		}
		
		return cnt;
	}

	// <로그인> 메소드
	@Override
	public MemberDto loginMember(Map<String, String> map) {
		
		MemberDto memberDto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();

			StringBuffer sql = new StringBuffer();
			sql.append("select name, id, emailid, emaildomain, joindate \n");
			sql.append("from member \n");
			sql.append("where id = ? and pass = ? \n");

			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, map.get("userid"));
			pstmt.setString(2, map.get("userpwd"));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				memberDto = new MemberDto();
				memberDto.setName(rs.getString("name"));
				memberDto.setId(rs.getString("id"));
				memberDto.setEmailid(rs.getString("emailid"));
				memberDto.setEmaildomain(rs.getString("emaildomain"));
				memberDto.setJoindate(rs.getString("joindate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return memberDto;
	}

	@Override
	public MemberDetailDto getMember(String id) {
		return null;
	}

	@Override
	public int modifyMember(MemberDetailDto memberDetailDto) {
		return 0;
	}

	// <탈퇴> 메소드
	@Override
	public int deleteMember(String id) {

		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.makeConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("delete \n");
			sql.append("from member_detail \n");
			sql.append("where id = ? \n");
			
			pstmt = conn.prepareStatement(sql.toString());
		
			pstmt.setString(1, id);
			
			cnt = pstmt.executeUpdate();
			
			pstmt.close();
			
			StringBuffer sql2 = new StringBuffer();
			sql2.append("delete \n");
			sql2.append("from member \n");
			sql2.append("where id = ? \n");
			
			pstmt = conn.prepareStatement(sql2.toString());
			pstmt.setString(1, id);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				DBClose.close(conn, pstmt);
		}
		
		return cnt;
	}

	

}
