package control;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.*;
import com.kitri.exception.AddException;
import com.kitri.service.OrderService;

@WebServlet("/addorder")
public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService service;
	
	public AddOrderServlet() {
		service = new OrderService();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1 장바구니 정보가 주문테이블에 저장
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("loginInfo");
		Customer c = new Customer();
		c.setId(id);
		
		OrderInfo info = new OrderInfo();
		info.setCustomer(c); //주문자 id 설정
		
		Map<Product, Integer>cart = (Map)session.getAttribute("cart");
		List<OrderLine> lines = new ArrayList();
		
		// 2 장바구니 상품번호, 수량->OrderLine에 설정
		for(Product p : cart.keySet()) {
			//String no = p.getProd_no();
			int quantity = (Integer)cart.get(p);
			OrderLine line = new OrderLine();
			
			line.setProduct(p);
			line.setOrder_quantity(quantity);
			lines.add(line);
		}
		
		info.setLines(lines);
		
		String result = ""; //주문 성공 여부 저장 변수 (1=성공 / -1=실패)
		
		try {
			
			service.addOrder(info);          // 주문 성공한 경우
			session.removeAttribute("cart"); // 장바구니 비우기
			result = "1";
			
		} catch (AddException e) {
			e.printStackTrace();
			result = "-1";
		}
		
		// 응답 (->addorderresult.jsp)
		String path = "/addorderresult.jsp";
		request.setAttribute("result", result);
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
	}

}
