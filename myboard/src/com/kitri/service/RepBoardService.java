package com.kitri.service;

import java.util.List;

import com.kitri.dao.RepBoardDao;
import com.kitri.dto.RepBoard;
import com.kitri.exception.AddException;

public class RepBoardService {
	
	private RepBoardDao dao;
	
	public RepBoardService() {
		dao = new RepBoardDao();
	}
	
	// <글 쓰기> 메소드
	public void write(RepBoard repBoard) throws AddException {
		repBoard.setParent_seq(0);
		dao.insert(repBoard);
	}
	
	// <답글 쓰기> 메소드
	public void reply(RepBoard repBoard) throws AddException {
		if(repBoard.getParent_seq() == 0) {
			throw new AddException("[답글 오류] : 부모 글 번호가 없는 답글입니다.");
		} else {
			dao.insert(repBoard);			
		}
	}

	// <글 목록 보기> 메소드
	public List<RepBoard> findByRows(int startRow, int endRow) {
		return dao.selectByRows(startRow, endRow);
	}

	// <총 게시글 수 얻기> 메소드
	public int getTotalPage() {
		return dao.selectTotalCnt();
	}
	
}
