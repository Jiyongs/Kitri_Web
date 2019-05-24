package com.kitri.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kitri.dto.RepBoard;
import com.kitri.exception.AddException;
import com.kitri.exception.NotFoundException;
import com.kitri.util.DBClose;
import com.kitri.util.DBConnection;

public class RepBoardDao {

	// 글 등록 insert
	public void insert(RepBoard repboard) throws AddException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
	
		StringBuffer sql = new StringBuffer();
		sql.append("insert into repboard(BOARD_SEQ, PARENT_SEQ,  BOARD_SUBJECT, BOARD_WRITER, ");
		sql.append("BOARD_CONTENTS, BOARD_DATE, BOARD_PASSWORD, BOARD_VIEWCOUNT) \n");
		sql.append("values(BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, systimestamp, ?, 0)");
	
		try {
			conn = DBConnection.makeConnection();
			pstmt = conn.prepareStatement(sql.toString());

			pstmt.setInt(1, repboard.getParent_seq());
			pstmt.setString(2, repboard.getBoard_subject());
			pstmt.setString(3, repboard.getBoard_writer());
			pstmt.setString(4, repboard.getBoard_contents());
			pstmt.setString(5, repboard.getBoard_password());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException("RepBoardDao : 글 등록 오류 - " + e.getMessage());
		} finally {
			DBClose.close(conn, pstmt);
		}
		
		System.out.println("RepBoardDao : 등록 완료");
		
	}
	
	// 글 목록 select
	public List<RepBoard> selectByRows(int startRow, int endRow) {
		
		List<RepBoard> list = new ArrayList<RepBoard>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * \n");
		sql.append("from (select rownum r, rb.* \n");
		sql.append("		from repboard rb \n");
		sql.append("		start with parent_seq = 0 \n");
		sql.append("		connect by prior board_seq = parent_seq \n");
		sql.append("		order siblings by board_seq desc) \n");
		sql.append("where r between ? and ?");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.makeConnection();
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RepBoard repBoard
				= new RepBoard(rs.getInt("parent_seq"),
							   rs.getString("board_subject"),
							   rs.getString("board_writer"), 
							   rs.getString("board_contents"),
							   rs.getString("board_password"));
				repBoard.setBoard_seq(rs.getInt("board_seq"));
				repBoard.setBoard_date(rs.getDate("board_date"));
				repBoard.setBoard_viewcount(rs.getInt("board_viewcount"));
				
				list.add(repBoard);
			}
			
		} catch (SQLException e) {
			e.printStackTrace(); // 목록 출력이므로, 에러는 출력만 하고 무시.
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	
	// 총 게시글 개수 select
	public int selectTotalCnt() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) \n");
		sql.append("from repboard");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int totalCnt = -1;
		
		try {
			conn = DBConnection.makeConnection();
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			rs.next();
			totalCnt = rs.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		
		return totalCnt;
	}

	
	
	
	
	
	public static void main(String[] args){
		
		RepBoardDao dao = new RepBoardDao();
		
//		try {
//			dao.insert(new RepBoard("글 제목입니다.", "작성자", "글 내용이다 글내용 글내용", "12345")); //글 쓰기용 테스트
//			//dao.insert(new RepBoard(1, "답글 제목입니다.", "작성자", "답글 내용이다 답글내용 답글내용", "12345")); //답글 쓰기용 테스트
//			
//			
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		
		for(RepBoard repBoard : dao.selectByRows(1, 8)) {
			System.out.println("글 번호 : " + repBoard.getBoard_seq());
			System.out.println("원본 글 번호 : " + repBoard.getParent_seq());
			System.out.println("글 제목 : " + repBoard.getBoard_subject());
			System.out.println("작성일" + repBoard.getBoard_date());
			System.out.println("작성자" + repBoard.getBoard_writer());
			System.out.println("글 내용" + repBoard.getBoard_contents());
			System.out.println("조회 수" + repBoard.getBoard_viewcount());
		}
		
		
	}


}
