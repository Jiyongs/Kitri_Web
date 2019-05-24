package com.kitri.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.PageBean;
import com.kitri.dto.RepBoard;
import com.kitri.service.RepBoardService;


@WebServlet("/boardlist")
public class RepBoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RepBoardService service;
	
	public RepBoardListServlet() {
		service = new RepBoardService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// [글 목록 출력]
		String cp = request.getParameter("currentPage");
		int currentPage = 1;  					// 보여줄 현재 페이지
		
		if(cp != null) {
			currentPage = Integer.parseInt(cp);
		}
		
		int cntPerPage = 10;  					// 페이지 별 보여줄 목록 수
		int totalCnt = service.getTotalPage();  // 총 게시글 수
		int cntPerPageGroup = 3;                // 그룹 페이지 수
		
		String url = "boardlist";

		PageBean pb = new PageBean(currentPage,
								   cntPerPage,
								   cntPerPageGroup,
								   totalCnt,
								   url);
		
		List<RepBoard> list =
				service.findByRows(pb.getStartRow(),
								   pb.getEndRow());
		
		pb.setList(list);
		request.setAttribute("pb", pb);

		String path = "/boardlistresult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
		// 현재 페이지,		startRow,	endRow
		// 1				1			10
		// 2				11			20
		// 5				41			50
		//[PageBean] int endRow = cntPerPage * currentPage;
		//[PageBean] int startRow = endRow - cntPerPage + 1;
		
		//[PageBean] request.setAttribute("boardlist", list);
		
		// [페이지 그룹핑]
		// 1. 총 페이지 수 계산
		//[PageBean] int totalPage = 1; 						// 총 페이지 수
		
		//totalPage = (cntPerPage==totalCnt ? totalCnt / cntPerPage : totalCnt / cntPerPage + 1);
		//[PageBean] totalPage = (int) Math.ceil((float)totalCnt/cntPerPage); //올림 함수
		
		//[PageBean] request.setAttribute("totalPage", totalPage);
		
		// 2. 시작페이지, 끝페이지 계산
		//[PageBean]	
//		int a = (int) Math.ceil((float)currentPage/cntPerPageGroup); // 현재페이지 / 그룹페이지 수를 올림 계산
//		
//		int endPage = cntPerPageGroup * a;
//		int startPage = endPage-cntPerPageGroup + 1;
//		if(currentPage == totalPage) {
//			endPage = totalPage;
//		}	
		
	}
	
}
