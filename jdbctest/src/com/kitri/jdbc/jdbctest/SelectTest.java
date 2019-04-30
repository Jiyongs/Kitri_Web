package com.kitri.jdbc.jdbctest;

import java.sql.*;
import java.util.*;

public class SelectTest {

	// [생성자]
	public SelectTest() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 완료");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// [메소드]
	// <DB 연결> 메소드
	public Connection makeConnection() throws SQLException {
		Connection conn = null;

		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.14.52:1521:orcl", "kitri", "kitri");
		return conn;
	}

	// <이름으로 member 찾기 - select> 메소드
	public List<MemberDto> memberList(String searchName) {

		List<MemberDto> list = new ArrayList<MemberDto>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// 2
			conn = makeConnection();

			// 3
			String sql = "";
			sql += "select no, name, id, joindate \n";
			sql += "from jdbctest \n";
			// searchName이 null이 아니면, where 조건을 붙임
			// null이면, 전체 검색
			if (searchName != null)
				sql += "where name = '" + searchName + "' \n";

			stmt = conn.createStatement();

			// 4
			rs = stmt.executeQuery(sql);
//			MemberDto memberDto = new MemberDto(); // list에는 MemberDto의 객체 주소가 저장되기 때문에, 객체를 while문 밖에 선언하게 되면, 같은 주소를 가진 객체들만 저장하여 결과적으로 마지막 결과값만 여러 개 뜨게 됨
			while (rs.next()) {
				MemberDto memberDto = new MemberDto();
				// memberDto.setNo(rs.getInt(1)); //열 순서로 지정 가능
				memberDto.setNo(rs.getInt("no")); // 별칭이 있다면, 별칭명을 써야 함
				memberDto.setId(rs.getString("id"));
				memberDto.setName(rs.getString("name"));
				memberDto.setJoindate(rs.getString("joindate"));

				list.add(memberDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 5
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	public List<MemberDto> memberSearch(int no) {

		List<MemberDto> list = new ArrayList<MemberDto>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {

			conn = makeConnection();
			stmt = conn.createStatement();

			String sql = "";
			sql += "select name, id, to_char(joindate, 'yy.mm.dd hh24:mi:ss') jd \n";
			sql += "from jdbctest \n";
			sql += "where no = '" + no + "'";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberDto memberDto = new MemberDto();
				memberDto.setName(rs.getString("name"));
				memberDto.setId(rs.getString("id"));
				memberDto.setJoindate(rs.getString("jd"));

				list.add(memberDto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// [실행 메소드]
	public static void main(String[] args) {

		// 1
		SelectTest st = new SelectTest();

		String searchName = null;

		List<MemberDto> list = st.memberList(searchName);
		System.out.println("회원 번호\t이름\t아이디\t가입일");
		System.out.println("-----------------------------------");

		for (MemberDto memberDto : list) {
			System.out.println(memberDto.getNo() + "\t" + memberDto.getName() + "\t" + memberDto.getId() + "\t"
					+ memberDto.getJoindate());
		}

//		int no = 10;
		int no = 201;
		
		System.out.println("\n\n회원 번호가 " + no + "인 회원 검색!!");
		List<MemberDto> list2 = st.memberSearch(no);
	
		if (list2.size() != 0) {
			for (MemberDto memberDto : list2) {
				System.out.println("이름 : " + memberDto.getName());
				System.out.println("id : " + memberDto.getId());
				String joindate = (memberDto.getJoindate()).substring(0, 8);
				if(joindate.equals("19.04.29")) {
					System.out.println("가입일 : " + memberDto.getJoindate().substring(9));					
				} else {
					System.out.println("가입일 : " + joindate);					
				}
			}
		} else {
			System.out.println(no + "번 회원은 존재하지 않습니다.");			
		}

		// 1)
		// 이름 : 홍길동
		// id : hong
		// 가입일 : 10:27:24 (오늘 - 시:분:초)
		// 19.04.25 (오늘x - 년.월.일)
		// 2)
		// 10번 회원은 존재하지 않습니다.
	}

}
