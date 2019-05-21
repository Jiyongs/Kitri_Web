package control;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.Product;

@WebServlet("/addcart")
public class AddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String quantity = request.getParameter("quantity");
		
		HttpSession session = request.getSession();
		
		// 세션에 set할 map(상품번호, 수량) 객체 생성
		Map<Product, Integer> c;
		
		// 기존 세션 내용 받아오기
		c = (Map) session.getAttribute("cart");
		
		// 1) 기존 세션 없는 경우 (장바구니 내용이 없음)
		if(c==null) {
			c = new HashMap<>();
			session.setAttribute("cart", c); 
		}
		
		// 2) 기존 세션 있는 경우 (장바구니 내용이 있음)
		// 세션에 넣을 값 마련
		Product p = new Product();
		p.setProd_no(no);
		int intQuantity = Integer.parseInt(quantity);
		
		// 2)-1. 장바구니에 해당 상품이 이미 존재하는지 확인
		Integer inte = c.get(p);
		// 존재하면,
		if(inte != null) {
			// 수량 합산
			intQuantity += inte.intValue();
		}
		
		// 장바구니에 상품 및 수량 추가
		c.put(p, intQuantity);
		
		String path = "/addcartresult.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
		
		/*
		 * [테스트]
		 * System.out.println("----- 장바구니 내용 ----");
		 * Set<Product> keys = c.keySet();
		 * //c라는 Map의 key값들만 받아옴 //set은 중복x, index x
		 * for(Product key : keys) { 
		 * 	int q = c.get(key); System.out.println("상품번호 : " + key.getProd_no() + " 수량 : " + q);
		 * }
		 */
		
	}


}
