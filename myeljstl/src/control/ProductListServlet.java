package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.Product;
import com.kitri.service.ProductService;


@WebServlet("/productlist")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private ProductService service;
       public ProductListServlet() {
    	   service = new ProductService(); //서비스 객체는 한 번만 만들어져야 하므로, 생성자에서 처리
       }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Product> list = service.findAll();     //상품 전체 목록 가져옴
		request.setAttribute("productlist", list);
		String path = "productlistresult.jsp";  // 만약 '/pro.jsp' 면 webcontext 바로 아래로 가게 되므로 주의
		RequestDispatcher rd = request.getRequestDispatcher(path);  //페이지 간 전달 가능한 객체 : servlet context(어플리케이션 전체에서 공유), request(응답하는 페이지까지만 공유), httpSession
		rd.forward(request, response);
	}



}
