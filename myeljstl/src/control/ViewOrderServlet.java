package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.OrderInfo;
import com.kitri.service.OrderService;

@WebServlet("/vieworder")
public class ViewOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService service;
	
	public ViewOrderServlet() {
		service = new OrderService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 로그인 정보 받아온 후, id 얻기
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("loginInfo");
		
		// id로 주문 내역(List) 찾기
		List<OrderInfo> orderList = service.findById(id);

		// 화면단 이동
		request.setAttribute("orderlist", orderList);
		// 어디로 이동할지 결정 ( -> vieworderresult.jsp )
		String path = "/vieworderresult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}


}
