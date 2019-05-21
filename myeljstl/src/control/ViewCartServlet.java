package control;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.Product;
import com.kitri.exception.NotFoundException;
import com.kitri.service.ProductService;

@WebServlet("/viewcart")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 서비스 선언
	ProductService service;

	public ViewCartServlet() {
		// 서비스 객체 생성
		service = new ProductService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1) 세션 객체 얻기
		HttpSession session = request.getSession();

		// 2) 세션 속성 중, "cart" 속성 얻기
		Map<Product, Integer> c = (Map) session.getAttribute("cart");

		Map<Product, Integer> rc = new HashMap<>();

		if (c != null) {
			Set<Product> keys = c.keySet();
			for (Product key : keys) {
				String no = key.getProd_no();

				// 서비스를 이용해 상품번호에 해당하는 Product 객체 얻기
				Product p1 = service.findByNo(no);
				// 수량 얻기
				int quantity = c.get(key);
				// 상품 정보 저장
				rc.put(p1, quantity);

			}

			request.setAttribute("rcart", rc);
			String path = "/viewcartresult.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(path);
			rd.forward(request, response);

		}

	}

}
