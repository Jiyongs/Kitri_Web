package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.kitri.dto.Customer;
import com.kitri.service.MemberService;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
	    String pass = request.getParameter("pass");
	    
	    MemberService service = new MemberService();
	    
	    // login()메소드에서 return값을 돌려받음.
	    String result = service.login(id, pass);
	    
	    HttpSession session = request.getSession();
	    session.removeAttribute("loginInfo");
	    
	    if (result.equals("1")) {
			session.setAttribute("loginInfo", id);
		}
	    
	    request.setAttribute("result", result);
	    
	    String path = "/loginresult.jsp";
	    
	    RequestDispatcher rd = request.getRequestDispatcher(path);
	    rd.forward(request, response);
	    
	}

}
